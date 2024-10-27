package com.todolist.app.model;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otp;
    private String type;
}
