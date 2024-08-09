package com.todolist.app.config;

import com.todolist.app.filter.JwtAuthenticationFilter;
import com.todolist.app.util.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter filter;

    private final JwtAuthenticationEntryPoint point;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        logger.info("Security filter chain => {}", http.toString());

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request.requestMatchers("/auth/**")
                        .permitAll()
                        .requestMatchers("/user/add").permitAll()
                        .anyRequest().authenticated()
                         )
                        .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy
                        .STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider doAuthenticationProvider()
    {
        DaoAuthenticationProvider doDaoAtuthenticationProvider = new DaoAuthenticationProvider();
        doDaoAtuthenticationProvider.setUserDetailsService(userDetailsService);
        doDaoAtuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return doDaoAtuthenticationProvider;
    }
}



