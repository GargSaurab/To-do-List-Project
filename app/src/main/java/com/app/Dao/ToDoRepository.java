package com.app.Dao;

import com.app.Entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<Integer, ToDo> {
}
