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

    // Register/Adds the user
    @PostMapping("/register")
     public ResponseEntity<CommonResponse> register(@Valid @RequestBody  UserRequest registerUser)
    {
        CommonResponse response = new CommonResponse();

            userSrv.register(registerUser);
            response.info.code = StatusCode.Success;
            response.info.message = "User got registered";
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // User can Reset the password
    @PostMapping("/resetPassword")
    public ResponseEntity<CommonResponse> resetPassword(@Valid @RequestBody PasswordReset userPasswordReset, HttpServletRequest request){
        CommonResponse response = new CommonResponse();
        userPasswordReset.setId(UUID.fromString(jwtHelper.extractId(request.getHeader("Authorization")))); // Fetching id from jwt

        LogUtil.debug(UserController.class, "UserController's resetPassword api starting");

            userSrv.resetPassword(userPasswordReset);
            response.info.code = StatusCode.Success;
            response.info.message = "Password got reset";
            return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // User can see there profile
    @GetMapping("/viewProfile")
    public ResponseEntity<CommonResponse> viewProfile(HttpServletRequest request)
    {
        CommonResponse response = new CommonResponse();
        LogUtil.info(UserController.class, "UserController's viewProfile api starting");

            // fetching id from jwt since we are not taking it from user
            String id = jwtHelper.extractId(request.getHeader("Authorization"));

            // UUID.fromString -> converting string into UUID
            UserDto user = userSrv.viewUser(UUID.fromString(id.trim()));
            response.info.code = StatusCode.Success;
            response.info.message = String.format("%s's data fetched", user.getUsername());
            response.data = user;

            return new ResponseEntity(response, HttpStatus.OK);

    }

}
