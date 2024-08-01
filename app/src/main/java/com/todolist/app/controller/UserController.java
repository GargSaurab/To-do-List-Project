package com.todolist.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.PasswordReset;
import com.todolist.app.dto.StatusCode;
import com.todolist.app.dto.UserRequest;
import com.todolist.app.service.UserService;

import jakarta.validation.Valid;

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




}
