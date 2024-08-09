package com.todolist.app.controller;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.dto.*;
import com.todolist.app.util.JwtHelper;
import com.todolist.app.service.UserService;
import com.todolist.app.util.LogUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userSrv;

    private final JwtHelper jwtHelper;

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
        LogUtil.debug(UserController.class, "UserController's resetPassword api starting");

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

    @GetMapping("/viewProfile")
    public ResponseEntity viewProfile(HttpServletRequest request)
    {
        CommonResponse response = new CommonResponse();
        LogUtil.info(UserController.class, "UserController's viewProfile api starting");

        String id = jwtHelper.extractId(request.getHeader("Authorization"));

        System.out.println(id.trim());

        try{
            UserDto user = userSrv.viewUser(UUID.fromString(id.trim()));

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
