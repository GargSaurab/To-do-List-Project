package com.todolist.app.dto;

import com.todolist.app.customAnnotation.ValidPattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest {
    private String name;
    private String password;
    private String captchaId;
    private String captcha;
}
