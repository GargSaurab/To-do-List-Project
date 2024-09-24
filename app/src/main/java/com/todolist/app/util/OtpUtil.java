package com.todolist.app.util;

import java.security.SecureRandom;

public class OtpUtil {

    public static String generateOtp(){

        SecureRandom random = new SecureRandom();
        StringBuilder otpString = new StringBuilder();

        for(int i = 0; i< 6; i++){

            otpString.append(random.nextInt(10));
        }

        return otpString.toString();
    }

    public static String getOtpHtml(String otp){

        return "<html>\n" +
                "<body>\n" +
                 "<p>Hello There!</p>,"+
                 "<p>Your OTP is : <b>"+otp+"</b></p>"+
                 "<br>" +
                        "<p>Thank you!</p>" +
                        "<p>ToDo App</p>" +
                    "</body>" +
               "</html>";
    }

}
