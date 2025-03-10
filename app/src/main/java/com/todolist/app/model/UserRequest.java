package com.todolist.app.model;

import com.todolist.app.customAnnotation.ValidPattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    @ValidPattern(type = "username", message = "Invalid Username")
    private String username;
    @ValidPattern(type = "emailId", message = "EmailId should be in proper format and can't be empty")
    private String email;
    @ValidPattern(type = "password", message = "Password is weak make it alphaumeric and include special characters")
    private String password;
}
