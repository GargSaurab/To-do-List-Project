package com.todolist.app.dto;

import com.todolist.app.customAnnotation.NonBlank;
import com.todolist.app.entity.Frequency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class ToDoRequest {
    @NotBlank(message = "OOPs! forgot to add the task")
    @Size(max = 50)
    private String task;
    @NonBlank(message = "Description shouldn't be null")
    @Size(max = 100)
    private String description;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private Frequency frequency;
    private UUID userId;
}
