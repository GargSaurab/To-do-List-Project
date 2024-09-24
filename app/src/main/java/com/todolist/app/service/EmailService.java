package com.todolist.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.todolist.app.customException.EmailSendingException;
import com.todolist.app.dto.StatusCode;
import com.todolist.app.util.LogUtil;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String email, String subject, String body) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom("noreply@todoapp.com");
            javaMailSender.send(message);
            LogUtil.info(EmailService.class, "Email sent successfully");
        } catch (MessagingException e) {
            LogUtil.error(EmailService.class, "MessagingException: " + e.getMessage());
            throw new EmailSendingException("Failed to send email due to invalid email format or messaging error.", StatusCode.BAD_REQUEST);
        } catch (MailException e) {
            LogUtil.error(EmailService.class, "MailException: " + e.getMessage());
            throw new EmailSendingException("Failed to send email due to mail server issue.", StatusCode.SERVER_ERROR);
        } catch (Exception e) {
            LogUtil.error(EmailService.class, "Unexpected error: " + e.getMessage());
            throw new EmailSendingException("An unexpected error occurred while sending the email.", StatusCode.SERVER_ERROR);
        }

    }

}
