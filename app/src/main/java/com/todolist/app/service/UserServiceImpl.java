package com.todolist.app.service;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dao.UserRepository;
import com.todolist.app.dto.PasswordReset;
import com.todolist.app.dto.UserDto;
import com.todolist.app.dto.UserRequest;
import com.todolist.app.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    public void register(UserRequest registerUser) {
        System.out.println(registerUser.toString());
        User user = mapper.map(registerUser, User.class);
        System.out.println(registerUser);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public void resetPassword(PasswordReset userPasswordReset) {
        User user = userRepo.findById(userPasswordReset.getId())
                .orElseThrow(() -> new RuntimeException("Some unusual server ERRORR!!!!"));
         String newPassword = encoder.encode(userPasswordReset.getNewPassword());
         // Validating if the user put the right old password or not if wrong process failed
        if(encoder.matches(userPasswordReset.getOldPassword(), user.getPassword()))
        {
            user.setPassword(newPassword);
        }else {
            throw new InvalidInputException("Wrong Password");
        }
        userRepo.save(user);
    }

    @Override
    public UserDto viewUser(UUID id) {
        // Fetching user via Id which is extracted throgh Jwt
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Some unusual server ERRORR!!!!"));
        UserDto userDto = mapper.map(user, UserDto.class);
        return userDto;
    }
}
