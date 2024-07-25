package com.app.service;

import com.app.Dto.ToDoDto;
import com.app.Entity.ToDo;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.List;

@Service
public interface TodoService {

    public List<ToDoDto> getTodoList(int id);

}
