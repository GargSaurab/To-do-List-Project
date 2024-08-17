package com.todolist.app.dto;

import com.todolist.app.entity.Frequency;
import com.todolist.app.entity.User;

import java.time.LocalDateTime;


public class ToDoDto {

    private int id;
    private String task;
    private String description;
    private boolean completed;
    private LocalDateTime start_Time;
    private LocalDateTime end_Time;
    private Frequency frequency;
    private User userId;

}
