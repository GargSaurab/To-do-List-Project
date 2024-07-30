package com.todolist.app.dao;

import com.todolist.app.entity.ToDo;
import com.todolist.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ToDo, Integer> {

    public User findByName(String username);

}
