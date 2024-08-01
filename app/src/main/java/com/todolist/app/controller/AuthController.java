package com.todolist.app.controller;

import com.todolist.app.dto.JwtRequest;
import com.todolist.app.dto.JwtResponse;
import com.todolist.app.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final AuthenticationManager authenticationManager;

    private final JwtHelper jwtHelper;

    public Logger logger = LoggerFactory.getLogger(AuthController.class);

     @PostMapping("/login")
     public ResponseEntity<?> login(@RequestBody @NotNull JwtRequest request)
     {
         // autheticate user
          this.doAutheticate(request.getName(), request.getPassword());

          // get userDetails
         UserDetails userDetails = userDetailsService.loadUserByUsername(request.getName());

         // generate token
         String token = this.jwtHelper.generateToken(userDetails);



         JwtResponse response = JwtResponse.builder()
                 .jwtToken(token)
                 .usernName(userDetails.getUsername())
//                 .time(LocalDateTime.now())
                 .build();

         return new ResponseEntity<>(response, HttpStatus.OK);

     }

     private void doAutheticate(String name, String password)
     {
         UsernamePasswordAuthenticationToken authentication
                 = new UsernamePasswordAuthenticationToken(name, password);

         try{
             authenticationManager.authenticate(authentication);
          }catch(Exception exception)
         {
             throw new BadCredentialsException("Wrong Input");
         }
     }
}