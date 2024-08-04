package com.todolist.app.service;

import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dao.UserRepository;
import com.todolist.app.entity.User;
import com.todolist.app.entity.UserCustomDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    private Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUsernameOrEmail(username);

        if(user != null)
        {
            logger.info(user.toString());

            return new UserCustomDetails(user);
        }

        throw new ResourceNotFoundException("user not found!!");
    }
}
