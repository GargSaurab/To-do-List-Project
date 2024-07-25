package com.app.service;

import com.app.CustomException.ResourceNotFoundException;
import com.app.Dao.ToDoRepository;
import com.app.Dto.ToDoDto;
import com.app.Entity.ToDo;
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
