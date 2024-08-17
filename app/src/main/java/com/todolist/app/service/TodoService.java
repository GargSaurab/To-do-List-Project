package com.todolist.app.service;

import com.todolist.app.dto.ToDoDto;
import com.todolist.app.dto.ToDoRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface TodoService {

    public List<ToDoDto> getTodoList(UUID id);
    public void addToDo(ToDoRequest toDoRequest);

}
