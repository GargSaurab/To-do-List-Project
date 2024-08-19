package com.todolist.app.dao;

import com.todolist.app.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Integer> {

    List<ToDo> findByUser_Id(UUID id);
}
