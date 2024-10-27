package com.todolist.app.service;

import com.todolist.app.model.OtpRequest;
import com.todolist.app.model.VerifyOtpRequest;
import com.todolist.app.model.VerifyOtpResponse;

public interface OtpService {

    String sendOtp(OtpRequest request);

     public VerifyOtpResponse verifyOtp(VerifyOtpRequest request);

}
