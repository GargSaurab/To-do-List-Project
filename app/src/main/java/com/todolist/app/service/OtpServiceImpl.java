package com.todolist.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.app.util.OtpUtil;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    EmailService emailService;
    
    @Override
    public String sendOtp(String email) {
        
          String otp = OtpUtil.generateOtp();
          String mailBody = OtpUtil.getOtpHtml(otp);
          emailService.sendMail( email, "ToDo App OTP", mailBody);            
          return "OTP sent successfully";
    }

}
