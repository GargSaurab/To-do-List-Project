package com.app.Dao;

import com.app.Entity.ToDo;
import com.app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ToDo, Integer> {
}
