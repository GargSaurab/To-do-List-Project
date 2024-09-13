package com.todolist.app.dto;

import lombok.Data;

import java.util.UUID;

import com.todolist.app.customAnnotation.ValidPattern;

@Data
public class PasswordReset {
    private UUID id;
    private String oldPassword;
    @ValidPattern(type = "password", message = "Password is weak make it alphaumeric and include special characters")
    private String newPassword;

}
