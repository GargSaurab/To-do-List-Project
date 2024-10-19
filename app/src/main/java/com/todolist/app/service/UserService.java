package com.todolist.app.service;

import org.springframework.stereotype.Service;

import com.todolist.app.model.PasswordReset;
import com.todolist.app.model.UserDto;
import com.todolist.app.model.UserRequest;

import java.net.PasswordAuthentication;
import java.util.UUID;

@Service
public interface UserService {

    void register(UserRequest registerUser);

    void resetPassword(PasswordReset userPasswordReset) ;

    UserDto viewUser(UUID id);
}
