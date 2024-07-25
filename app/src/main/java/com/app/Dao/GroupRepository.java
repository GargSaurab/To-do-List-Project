package com.app.Dao;

import com.app.Entity.Group;
import com.app.Entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ToDo, Integer> {
}
