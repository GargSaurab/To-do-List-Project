package com.todolist.app.controller;

import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.JwtRequest;
import com.todolist.app.dto.JwtResponse;
import com.todolist.app.util.JwtHelper;
import com.todolist.app.util.LogUtil;
import com.todolist.app.util.Utility;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
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

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor // constructor Injection
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper jwtHelper;

     @PostMapping("/login")
     public ResponseEntity<CommonResponse> login(@RequestBody @NotNull JwtRequest request)
     {
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
         CommonResponse commonResponse = Utility.success("Successfull login", response);
         return new ResponseEntity<>(commonResponse, HttpStatus.OK);
     }

     private void doAutheticate(String name, String password)
     {
         UsernamePasswordAuthenticationToken authentication
                 = new UsernamePasswordAuthenticationToken(name, password);
         try {
            // Authenticating user via
             manager.authenticate(authentication);
         } catch (BadCredentialsException e) {
             LogUtil.error(AuthController.class, "Bad credentials for user: {}" + name);
             throw new BadCredentialsException(" Wrong credentials");
         } catch (Exception e) {
             LogUtil.error(AuthController.class,"Authentication failed for user: {}" + name + e);
             throw new RuntimeException("Some error occured in Server");
         }
     }
}