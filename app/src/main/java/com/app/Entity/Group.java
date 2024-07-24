package com.app.Entity;

import jakarta.persistence.*;

public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User adminId;
}
