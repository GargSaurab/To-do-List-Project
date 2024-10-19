package com.todolist.app.model;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private String role;
}
