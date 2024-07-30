package com.todolist.app.service;

import com.todolist.app.dto.ToDoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {

    public List<ToDoDto> getTodoList(int id);

}
