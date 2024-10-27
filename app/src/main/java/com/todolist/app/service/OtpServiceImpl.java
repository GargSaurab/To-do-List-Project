package com.todolist.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.app.customException.RedisException;
import com.todolist.app.model.OtpRequest;
import com.todolist.app.model.VerifyOtpRequest;
import com.todolist.app.model.VerifyOtpResponse;
import com.todolist.app.util.LogUtil;
import com.todolist.app.util.OtpUtil;
import com.todolist.app.util.StatusCode;
import com.todolist.app.util.Utility;

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
    emailService.sendMail(email, "ToDo App OTP", mailBody);
    if (redisService.hasKey(redisKey)) {
      redisService.delete(redisKey);
    }
    redisService.set(redisKey, otp, null);
    return "OTP sent successfully";
  }

  public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
    String redisKey = "otp:" + request.getEmail();
    try {
      String redisOtp = redisService.get(redisKey, String.class);
      if (Utility.isEmpty(redisOtp)) {
        LogUtil.warn(OtpServiceImpl.class, "OTP not found for email: " + request.getEmail());
        return new VerifyOtpResponse(false, true);
      } else {
        if (redisOtp.equals(request.getOtp())) {
          return new VerifyOtpResponse(true, false);
        } else {
          return new VerifyOtpResponse( false, false);
        }
      }
    } catch (Exception e) {
      throw new RedisException("Some error occurred while verifying OTP", StatusCode.SERVER_ERROR, e);
    }
  }

}
