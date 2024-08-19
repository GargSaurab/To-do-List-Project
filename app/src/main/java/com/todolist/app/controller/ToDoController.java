package com.todolist.app.controller;

import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.StatusCode;
import com.todolist.app.dto.ToDoDto;
import com.todolist.app.dto.ToDoRequest;
import com.todolist.app.service.TodoService;
import com.todolist.app.util.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor // for constructor injection makes the injection confirmed
public class ToDoController {

    private final JwtHelper jwtHelper;
    private final TodoService tdSrv; // makes the class immutable which helps in
    // thread safety, security and simplicity.
    // Constructor Injection also allows it.

     @GetMapping("/todolist")
    public ResponseEntity<?> getTodoList(HttpServletRequest request)
    {
          String id = jwtHelper.extractId(request.getHeader("Authorization"));

          try{
            List<ToDoDto> todos = tdSrv.getTodoList(UUID.fromString(id));
          return new ResponseEntity(todos, HttpStatus.OK);
             }catch(Exception exception)
          {
              throw exception;
          }
    }

    @PostMapping("/addTodo")
    public ResponseEntity<?> addTodo(@Valid @RequestBody ToDoRequest toDoRequest, HttpServletRequest request)
    {
        toDoRequest.setUserId(UUID.fromString(jwtHelper.extractId(request.getHeader("Authorization"))));
        CommonResponse response = new CommonResponse();

        try{
            tdSrv.addToDo(toDoRequest);
            response.info.code = StatusCode.success;
            response.info.message = "Todo is saved";
            return ResponseEntity.ok(response);
        }catch (Exception exception)
        {
           throw exception;
        }
    }

    @PostMapping("/removeTodo")
    public ResponseEntity<?> removeTodo(@RequestParam int id)
    {
        CommonResponse response = new CommonResponse();

        try{
            tdSrv.removeTodo(id);
            response.info.code = StatusCode.success;
            response.info.message = "Todo is removed";
            return ResponseEntity.ok(response);

        }catch(Exception exception)
        {
            throw exception;
        }
    }

    @PostMapping("/updateTodo")
    public ResponseEntity<?> updateTodo(@Valid @RequestBody ToDoDto toDoDto)
    {
        CommonResponse response = new CommonResponse();

        try{
            tdSrv.updateTodo(toDoDto);
            response.info.code = StatusCode.success;
            response.info.message = "Todo is updated";
            return ResponseEntity.ok(response);

        }catch(ResourceNotFoundException re)
        {
            throw re;
        }catch(Exception exception)
        {
            throw exception;
        }
    }


}

/*
   Constructor injection offer's many advantages ->

    * Explicit Dependencies: Makes required dependencies clear and explicit.
    * Compile-Time Safety: Resolves dependencies at compile time, reducing runtime errors. We don't have to check if
                         dependency injected or not.
    * Immutable Dependencies: Ensures dependencies cannot be changed after instantiation, promoting immutability.
                            Which helps in thread safety and concurrency.
    * Testability:  Constructor injection facilitates easier unit testing by allowing dependencies to be mocked or
                  stubbed during testing.
    * Clear Object Lifecycle: Dependencies are satisfied during object creation, simplifying lifecycle management.

   Since Spring Framework 4.3, the use of @Autowired is optional for constructors if the class
     has only one constructor.
 */

