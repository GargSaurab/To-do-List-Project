package com.todolist.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.app.model.OtpRequest;
import com.todolist.app.util.OtpUtil;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    EmailService emailService;
    @Autowired
    RedisService redisService;
    
    @Override
    public String sendOtp(OtpRequest request) {   
          String otp = OtpUtil.generateOtp();
          String email = request.getEmail();
          String redisKey = "otp:" + email;
          String mailBody = OtpUtil.getOtpHtml(otp, request.getType(), request.getUserName());
        //   emailService.sendMail(email, "ToDo App OTP", mailBody);
          if(redisService.hasKey(redisKey)) {
              redisService.delete(redisKey);
          }
          redisService.set(redisKey, otp, null);
          String redisOtp = redisService.get(redisKey, String.class);
          return "OTP sent successfully";
    }
    

}
