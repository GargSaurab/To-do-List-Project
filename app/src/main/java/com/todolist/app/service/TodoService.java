package com.todolist.app.service;

import com.todolist.app.dto.ToDoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TodoService {

    public List<ToDoDto> getTodoList(UUID id);

}
