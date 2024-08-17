package com.todolist.app.service;

import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dao.ToDoRepository;
import com.todolist.app.dao.UserRepository;
import com.todolist.app.dto.ToDoDto;
import com.todolist.app.dto.ToDoRequest;
import com.todolist.app.entity.ToDo;
import com.todolist.app.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final ToDoRepository tdRep;

    private final UserRepository userRep;

    private final ModelMapper mapper;

    @Override
    public List<ToDoDto> getTodoList(UUID id) {

       List<ToDoDto> todos = tdRep.findByUser_Id(id).stream().map(todo -> mapper.map(todo, ToDoDto.class)).collect(Collectors.toList());

       if(todos.isEmpty())
       {
           throw new ResourceNotFoundException("No todos found");
       }
        return todos;
    }

    @Override
    public void addToDo(ToDoRequest toDoRequest) {

        User user = userRep.findById(toDoRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Some error occured"));

        ToDo todo = mapper.map(toDoRequest, ToDo.class);
        todo.setUser(user);
        tdRep.save(todo);
    }


}
