package com.todolist.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.todolist.app.filter.JwtAuthenticationFilter;
import com.todolist.app.security.OAuthAuthenticationSuccessHandler;
import com.todolist.app.util.JwtAuthenticationEntryPoint;
import com.todolist.app.util.LogUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity// This annotation helps in defining the access control of speicific methods or service to specific user/roles
//@EnableWebSecurity This annotation is used to enable Spring Security's web security features. but is
//No longer needed manually in Spring Security 5.7+. As of this version, @EnableWebSecurity is implicitly applied
// by Spring when Spring Security is on the classpath, so you do not need to add it explicitly.
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter filter;

    private final JwtAuthenticationEntryPoint point;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    // Handles the OAuth2 authentication after success
    private final OAuthAuthenticationSuccessHandler handler;

    /**
     * This Bean is responsible for configuring the {@link SecurityFilterChain} used by Spring
     * Security to protect the application. It disables CSRF protection, allows access to the
     * "/auth/**" and "/oauth2/**" routes, and allows access to the "/user/add" route. All other
     * routes are protected by authentication. It also configures the OAuth2 login process to
     * use the {@link OAuthAuthenticationSuccessHandler} to handle successful authentication.
     * Finally, it configures the session management to use a stateless session, meaning that
     * the application does not store any session information on the server.
     *
     * @param http the {@link HttpSecurity} builder used to configure the security filter
     *             chain.
     * @return the configured {@link SecurityFilterChain}.
     * @throws Exception if an error occurs while building the security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        LogUtil.info(SecurityFilterChain.class,"Security filter chain => " + http.toString());
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**", "/oauth2/**", "/login/**")
                        .permitAll()
                        .requestMatchers("/user/add").permitAll()
                        .anyRequest().authenticated()
                )// Protect all other routes
                .oauth2Login(oauth -> oauth
                        .successHandler(handler))
                        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy
                        .STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * This Bean is responsible for authenticating the user using the username and password,
     * the {@link UserDetailsService} is used to load the user details from the database and
     * the {@link PasswordEncoder} is used to check the password.
     * @return a new instance of {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider doAuthenticationProvider()
    {
        DaoAuthenticationProvider doDaoAtuthenticationProvider = new DaoAuthenticationProvider();
        doDaoAtuthenticationProvider.setUserDetailsService(userDetailsService);
        doDaoAtuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return doDaoAtuthenticationProvider;
    }
}

// Spring Security can manage the whole OAuth flow all by itself
// with the help of sucessHandler i.e we don't need to add it's controller.
/*
* User logs in via OAuth2 (Google, GitHub, etc.) through the frontend.
    The backend (Spring Security) handles the OAuth2 flow and triggers the OAuthSuccessHandler.
    The success handler generates both an access token (JWT) and a refresh token.
    The frontend uses the access token to authenticate requests.
    If the access token expires, the frontend can request a new access token using the refresh token.
 */



