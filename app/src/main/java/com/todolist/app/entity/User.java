package com.todolist.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = true, unique = true)
    private String username;
    @Column(nullable = true, unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Provider provider;
    private String role;
}
