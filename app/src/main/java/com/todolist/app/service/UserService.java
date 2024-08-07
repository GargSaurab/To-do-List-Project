package com.todolist.app.service;

import com.todolist.app.dto.PasswordReset;
import com.todolist.app.dto.UserDto;
import com.todolist.app.dto.UserRequest;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.UUID;

@Service
public interface UserService {

    public void register(UserRequest registerUser);

    public void resetPassword(PasswordReset userPasswordReset) ;

    public UserDto viewUser(UUID id);
}
