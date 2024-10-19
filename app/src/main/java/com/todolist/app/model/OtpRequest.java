package com.todolist.app.model;

import lombok.Data;

@Data
public class OtpRequest {
    private String email;
    private String userName;
    private String type;
}
