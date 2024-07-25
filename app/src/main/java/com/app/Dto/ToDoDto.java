package com.app.Dto;

import com.app.Entity.User;

import java.time.LocalDateTime;


public class ToDoDto {

    private int id;
    private String task;
    private String description;
    private boolean completed;
    private LocalDateTime start_Time;
    private LocalDateTime end_Time;
    private User user;

}
