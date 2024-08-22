package com.todolist.app.service;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dao.ToDoRepository;
import com.todolist.app.dao.UserRepository;
import com.todolist.app.dto.ToDoDto;
import com.todolist.app.dto.ToDoRequest;
import com.todolist.app.entity.ToDo;
import com.todolist.app.entity.User;
import com.todolist.app.util.Utility;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
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
       if(Utility.isDateTimeValid(toDoRequest.getStartDate(), toDoRequest.getEndDate(),toDoRequest.getStartTime(), toDoRequest.getEndTime()))
        {
           User user = userRep.findById(toDoRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Some error occured"));

            ToDo todo = mapper.map(toDoRequest, ToDo.class);
            todo.setUser(user);
            tdRep.save(todo);
        }else{

           throw new InvalidInputException("End date/time cannot be before start date/time");
       }
    }

    @Override
    public void removeTodo(int id) {
        tdRep.deleteById(id);
    }

    @Override
    public void updateTodo(ToDoDto toDoDto) {

        if(Utility.isDateTimeValid(toDoDto.getStartDate(), toDoDto.getEndDate(),toDoDto.getStartTime(), toDoDto.getEndTime()))
        {
            ToDo todo = tdRep.findById(toDoDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Can't find the todo in database"));

            // Map only non-null fields from ToDoDto to the existing ToDo entity
            mapper.getConfiguration().setSkipNullEnabled(true);
            mapper.map(toDoDto, todo);

            if (todo.getEndDate().isBefore(todo.getStartDate())) {
                todo.setEndDate(toDoDto.getStartDate());

                if(todo.getEndTime().isBefore(todo.getStartTime())){
                    todo.setEndTime( LocalTime.of(23, 59, 59));
                }
            }

            tdRep.save(todo);
        }else{

            throw new InvalidInputException("End date/time cannot be before start date/time");
        }

    }

}
