package com.todolist.app.dao;

import com.todolist.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("Select  u FROM User u WHERE u.username = :username or u.email = :username")
    Optional<User> findByUsernameOrEmail(@Param("username") String username);

    Optional<User> findByEmail(String email);
}
