package com.todolist.app.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PasswordReset {

    private UUID id;
    private String oldPassword;
    private String newPassword;

}
