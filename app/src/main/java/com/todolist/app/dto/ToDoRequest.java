package com.todolist.app.dto;

import com.todolist.app.entity.Frequency;
import com.todolist.app.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class ToDoRequest {

    @NotNull
    @Size(max = 50)
    private String task;
    @NotNull
    @Size(max = 50)
    private String description;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Frequency frequency;
    private UUID userId;
}
