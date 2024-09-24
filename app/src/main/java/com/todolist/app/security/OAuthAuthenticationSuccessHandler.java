package com.todolist.app.security;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todolist.app.dao.UserRepository;
import com.todolist.app.entity.Provider;
import com.todolist.app.entity.User;
import com.todolist.app.entity.UserCustomDetails;
import com.todolist.app.util.JwtHelper;
import com.todolist.app.util.LogUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    /**
     * This class is responsible for handling the OAuth2 login success response.
     * This class is responsible for generating a JWT token and returning it to the client.
     * This class is a part of the Spring Security OAuth2 configuration.
     */

    @SuppressWarnings("null")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (authentication instanceof OAuth2AuthenticationToken) {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oauthToken.getPrincipal();
        LogUtil.info(OAuthAuthenticationSuccessHandler.class,oAuth2User.toString());
        String provider = oauthToken.getAuthorizedClientRegistrationId(); // Extract the provider
        LogUtil.info(OAuthAuthenticationSuccessHandler.class,provider);
        String email = oAuth2User.getAttribute("email");
        LogUtil.info(OAuthAuthenticationSuccessHandler.class,email);
        User user = userRepository.findByEmail(email).orElseGet(()->{
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setUsername(email.substring(0, email.indexOf("@")));
                    newUser.setProvider(Provider.valueOf(provider.toUpperCase()));
                    return userRepository.save(newUser);
                });
        String token = jwtHelper.generateToken(new UserCustomDetails(user));
        LogUtil.info(OAuthAuthenticationSuccessHandler.class,token);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Map.of("token", token, "provider", provider)));
        }else{
            LogUtil.error(OAuthAuthenticationSuccessHandler.class,"Authentication not supported");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication type not supported.");
        }
    }
}
