package com.todolist.app.controller;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.dto.*;
import com.todolist.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userSrv;

    @PostMapping("/register")
     public ResponseEntity<?> register(@Valid @RequestBody  UserRequest registerUser)
    {
        CommonResponse response = new CommonResponse();

            userSrv.register(registerUser);
            response.info.code = StatusCode.success;
            response.info.message = "User got registered";
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody PasswordReset userPasswordReset){
        CommonResponse response = new CommonResponse();

        try{
            userSrv.resetPassword(userPasswordReset);
            response.info.code = StatusCode.success;
            response.info.message = "Password got reset";
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(InvalidInputException ie)
        {
            throw ie;
        }catch(Exception exception)
        {
            throw exception;
        }
    }

    @GetMapping("/view")
    public ResponseEntity viewUser(@RequestBody UUID id)
    {
        CommonResponse response = new CommonResponse();

        try{
            UserDto user = userSrv.viewUser(id);

            response.info.code = StatusCode.success;
            response.info.message = String.format("%s's data fetched", user.getUsername());
            response.data = user;

            return new ResponseEntity(response, HttpStatus.OK);
        }catch(Exception exception)
        {
            throw exception;
        }
    }




}
