package com.app.Dao;

import com.app.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Integer, Group> {
}
