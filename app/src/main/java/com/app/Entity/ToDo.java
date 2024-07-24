package com.app.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
