package com.todolist.app.service;

import org.springframework.stereotype.Service;

import com.todolist.app.model.ToDoDto;
import com.todolist.app.model.ToDoRequest;

import java.util.List;
import java.util.UUID;

@Service
public interface TodoService {
    List<ToDoDto> getTodoList(UUID id);
    void addToDo(ToDoRequest toDoRequest);
    void removeTodo(int id);
    void updateTodo(ToDoDto toDoDto);
}
