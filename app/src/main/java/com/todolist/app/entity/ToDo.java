package com.todolist.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_todo")
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String task;
    private String description;
    private boolean completed;
    private LocalDateTime start_Time;
    private LocalDateTime end_Time;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
