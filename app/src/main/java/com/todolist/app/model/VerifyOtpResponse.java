package com.todolist.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyOtpResponse {
    private boolean isVerified;
    private boolean isExpired;
}
