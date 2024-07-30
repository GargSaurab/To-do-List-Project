package com.todolist.app.service;

import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dao.ToDoRepository;
import com.todolist.app.dto.ToDoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    private ToDoRepository tdRep;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<ToDoDto> getTodoList(int id) {

       List<ToDoDto> todos = tdRep.findById(id).stream().map(todo -> mapper.map(todo, ToDoDto.class)).collect(Collectors.toList());

       if(todos.isEmpty())
       {
           throw new ResourceNotFoundException("No todos found");
       }
        return todos;
    }
}
