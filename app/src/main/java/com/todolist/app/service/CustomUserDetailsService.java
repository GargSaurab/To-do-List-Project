package com.todolist.app.service;

import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dao.UserRepository;
import com.todolist.app.entity.User;
import com.todolist.app.entity.UserCustomDetails;
import com.todolist.app.util.LogUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsernameOrEmail(username)
                .orElseThrow(() ->  new ResourceNotFoundException("user not found!!"));
        LogUtil.info(CustomUserDetailsService.class,user.toString());
        return new UserCustomDetails(user);

    }
}
