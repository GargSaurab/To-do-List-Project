package com.todolist.app.util;

import java.security.SecureRandom;

public class OtpUtil {

    public static String generateOtp() {
        SecureRandom random = new SecureRandom();
        StringBuilder otpString = new StringBuilder();
        for (int i = 0; i < 6; i++) {

            otpString.append(random.nextInt(10));
        }
        return otpString.toString();
    }

    public static String getOtpHtml(String otp, String type, String userName) {
        if (type.equals("signup")) {
            return singUpHtml(otp, userName);
        } else if (type.equals("resetPassword")) {
            return resetPasswordHtml(otp, userName);
        } else {
            return loginHtml(otp, userName);
        }
    }

    public static String singUpHtml(String otp, String userName) {
        return "<html>" +
                "<body>" +
                "<p>Hello There! " + userName + ",</p>" +
                "<p>Welcome to ToDo App" +
                "<br>" +
                "Your OTP is : <b>" + otp + "</b> <br>" +
                "<br>" +
                "This OTP is valid for the next 4 minutes. Do not share this code with anyone.<p>" +
                "<br>" +
                "<p>Thank you!</p>" +
                "<p>ToDo App Team</p>" +
                "</body>" +
                "</html>";
    }

    public static String resetPasswordHtml(String otp, String userName) {
        return "<html>" +
                "<body>" +
                "<p>Hello " + userName + ",</p>" +
                "<p>It looks like you requested to reset your password for Todo App, Please use the OTP below to proceed with the password reset:<br>"
                +
                "Your OTP is : <b>" + otp + "</b> <br>" +
                "This OTP is valid for the next 4 minutes. Do not share this code with anyone.<br>" +
                "If you did not request this, please contact our support team immediately or ignore this email.</p>" +
                "<br>" +
                "<p>Thank you!</p>" +
                "<p>ToDo App Team</p>" +
                "</body>" +
                "</html>";
    }

    public static String loginHtml(String otp, String userName) {
        return "<html>" +
                "<body>" +
                "<p>Hello " + userName + ",</p>" +
                "<p> Please use the OTP below to complete your login for Todo App:<br>" +
                "<p>Your OTP is : <b>" + otp + "</b></p>" +
                "This OTP is valid for the next 4 minutes. Do not share this code with anyone.<br>" +
                "If this wasn't you, please secure your account by contacting our support team immediately." +
                "<br>" +
                "<p>Thank you!</p>" +
                "<p>ToDo App Team</p>" +
                "</body>" +
                "</html>";
    }
}
