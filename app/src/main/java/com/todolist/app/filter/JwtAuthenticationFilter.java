package com.todolist.app.filter;

import com.todolist.app.util.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // Constructor Injection
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    private final JwtHelper jwtHelper;

    private final UserDetailsService userDetailsService;

    // Fetching barer token from Authroziation header
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        if(requestHeader != null && requestHeader.startsWith("Bearer"))
        {
            token = requestHeader.substring(7); // Skiping the Bearer and retrieving Jws from token

            try{
                username = this.jwtHelper.getUsernameFromToken(token);
            }catch(IllegalArgumentException exception)
            {
                logger.info("Illegal Argument while fetching the username");

                exception.printStackTrace();
            }
            catch(ExpiredJwtException exception)
            {
                logger.info("Provided Token expired");
                exception.printStackTrace();
            } catch(MalformedJwtException exception)
            {
                logger.info("Some change has done in token, INVALID TOKEN!");
                exception.printStackTrace();
            } catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }else{
            logger.info("Invalid Header value!!");
        }

     // Checks if username and if it's  authenticated from the SecurityContextHolder
    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
    {
        // fetched user details via username
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        // Validates token
        Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

        if(validateToken)
        {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // Setting the authentication as token is validated
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }else{
            logger.info("validation fails!!");
        }
    }

    // called to pass control to the next filter in the line, if not present then to the resource itself
   filterChain.doFilter(request, response);

    }
}
