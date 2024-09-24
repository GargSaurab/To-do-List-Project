package com.todolist.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.PasswordReset;
import com.todolist.app.dto.UserDto;
import com.todolist.app.dto.UserRequest;
import com.todolist.app.service.UserService;
import com.todolist.app.util.LogUtil;
import com.todolist.app.util.Utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userSrv;

    /**
     * Register/Adds the user
     * 
     * @param registerUser The user's information
     * @return Success Message
     */
    @PostMapping("/register")
    public ResponseEntity<CommonResponse> register(@Valid @RequestBody UserRequest registerUser) {
        userSrv.register(registerUser);
        CommonResponse response = Utility.success("User got registered");
        return ResponseEntity.ok(response);
    }

    /**
     * User can Reset the password
     * 
     * @param userPasswordReset The passwordReset object which contains the old
     *                          password and the new password
     * @param request           The HttpServletRequest to fetch the jwt
     * @return Success Message
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<CommonResponse> resetPassword(@Valid @RequestBody PasswordReset userPasswordReset,
            HttpServletRequest request) {
        // Fetching id from jwt
        userPasswordReset.setId(Utility.getUserId(request));
        LogUtil.debug(UserController.class, "UserController's resetPassword api starting");
        userSrv.resetPassword(userPasswordReset);
        CommonResponse response = Utility.success("Password got reset");
        return ResponseEntity.ok(response);
    }

    /**
     * User can see there profile
     * 
     * @param request The HttpServletRequest to fetch the jwt
     * @return User's data
     */
    @GetMapping("/viewProfile")
    public ResponseEntity<CommonResponse> viewProfile(HttpServletRequest request) {
        LogUtil.info(UserController.class, "UserController's viewProfile api starting");
        UserDto user = userSrv.viewUser(Utility.getUserId(request));
        CommonResponse response = Utility.success(String.format("%s's data fetched", user.getUsername()), user);
        return ResponseEntity.ok(response);
    }

}
