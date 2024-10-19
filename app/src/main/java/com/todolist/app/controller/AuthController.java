package com.todolist.app.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.app.model.JwtRequest;
import com.todolist.app.model.JwtResponse;
import com.todolist.app.model.OtpRequest;
import com.todolist.app.service.OtpService;
import com.todolist.app.util.CustomResponse;
import com.todolist.app.util.JwtHelper;
import com.todolist.app.util.LogUtil;
import com.todolist.app.util.Utility;

import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * This controller is responsible for handling the user authentication
 * using the JWT as the return object.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // constructor Injection
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final OtpService otpService;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;

    /**
     * This endpoint is responsible for handling the user authentication
     * with the help of JWT.
     * <p>
     * This endpoint will return the JWT token in the response body.
     * </p>
     *
     * @param request The request object containing the user credentials.
     * @return The Response containing the JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@RequestBody @NonNull JwtRequest request) {
        // autheticate user
        this.doAutheticate(request.getName(), request.getPassword());
        // get userDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getName());
        // generate token
        String token = this.jwtHelper.generateToken(userDetails);
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername())
                .time(LocalDateTime.now())
                .build();
                CustomResponse customResponse = Utility.success("Successfull login", response);
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<CustomResponse> sendOtp(@RequestBody OtpRequest request) { 
        String otpResponse = otpService.sendOtp(request);
        CustomResponse response = Utility.success(otpResponse);
        return ResponseEntity.ok(response);
    }

    /**
     * This method is responsible for authenticating the user using the username and
     * password.
     * <p>
     * If the authentication is successful, nothing happens.
     * </p>
     * <p>
     * If the authentication fails, a RuntimeException is thrown.
     * </p>
     *
     * @param name     The username of the user.
     * @param password The password of the user.
     */
    private void doAutheticate(String name, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(name, password);
        try {
            // Authenticating user via AuthenticationManager
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            LogUtil.error(AuthController.class, "Bad credentials for user: {}" + name);
            throw new BadCredentialsException("Wrong credentials");
        } catch (Exception e) {
            LogUtil.error(AuthController.class, "Authentication failed for user: {}" + name + e);
            throw new RuntimeException("Some error occured in Server");
        }
    }
}