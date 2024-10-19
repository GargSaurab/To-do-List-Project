package com.todolist.app.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class JwtResponse {

    private String jwtToken;
    private String userName;
    private LocalDateTime time;
}
