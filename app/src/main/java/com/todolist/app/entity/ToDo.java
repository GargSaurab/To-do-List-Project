package com.todolist.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_todo")
public class ToDo {

    @PrePersist
    public void prePersist(){

        if(startTime == null)
        {
            startTime = LocalTime.now();
        }

        if(startDate == null)
        {
            startDate = LocalDate.now();
        }

        if(endDate == null)
        {
            endDate = LocalDate.now();
        }
        if(endTime == null)
        {
            endTime = LocalTime.of(23, 59, 59);
        }

        if(frequency == null)
        {
            frequency = Frequency.NONE;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    @Size(max = 50)
    private String task;

    @Column( nullable = false, length = 50)
    @Size(max = 50)
    private String description;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean completed;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Frequency frequency;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

}
