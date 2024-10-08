package com.todolist.app.dao;

import com.todolist.app.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ToDo, Integer> {
}
