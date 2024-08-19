package com.todolist.app.dto;

import com.todolist.app.customAnnotation.ValidPattern;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
