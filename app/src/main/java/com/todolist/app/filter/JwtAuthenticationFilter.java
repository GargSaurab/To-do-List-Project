package com.todolist.app.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todolist.app.util.JwtHelper;
import com.todolist.app.util.LogUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // Constructor Injection
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHelper jwtHelper;
    private final UserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        
        String path = request.getServletPath();
        LogUtil.info(JwtAuthenticationFilter.class, path);
        
        // Skip JWT filter for OAuth2 endpoints
        return path.startsWith("/login") || path.startsWith("/auth");
    }

    // Fetching barer token from Authroziation header
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            token = requestHeader.substring(7); // Skiping the Bearer and retrieving Jws from token
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException exception) {
                LogUtil.error(JwtAuthenticationFilter.class, "Illegal Argument while fetching the username");
                exception.printStackTrace();
            } catch (ExpiredJwtException exception) {
                LogUtil.error(JwtAuthenticationFilter.class, "Provided Token expired");
                exception.printStackTrace();
            } catch (MalformedJwtException exception) {
                LogUtil.error(JwtAuthenticationFilter.class, "Some change has done in token, INVALID TOKEN!");
                exception.printStackTrace();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            LogUtil.error(JwtAuthenticationFilter.class, "Invalid Header value!!");
        }
        // Checks if username and if it's authenticated from the SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // fetched user details via username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            // Validates token
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
            if (validateToken) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Setting the authentication as token is validated
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                LogUtil.error(JwtAuthenticationFilter.class, "validation fails!!");
            }
        }
        // called to pass control to the next filter in the line, if not present then to
        // the resource itself
        filterChain.doFilter(request, response);
    }
}
