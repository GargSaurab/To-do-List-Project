package com.todolist.app.dto;

import com.todolist.app.entity.Frequency;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class ToDoDto {

    private int id;
    private String task;
    private String description;
    private boolean completed;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Frequency frequency;
    private UUID userId;

}
