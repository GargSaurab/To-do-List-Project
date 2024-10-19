package com.todolist.app.service;

import com.todolist.app.model.OtpRequest;

public interface OtpService {

    String sendOtp(OtpRequest request);

}
