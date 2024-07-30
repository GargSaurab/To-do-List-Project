package com.todolist.app.controller;

import com.todolist.app.dto.ToDoDto;
import com.todolist.app.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todolist")
@RequiredArgsConstructor // for constructor injection makes the injection confirmed
public class ToDoController {

    private final TodoService tdSrv; // makes the class immutable which helps in
    // thread safety, security and simplicity.
    // Constructor Injection also allows it.

    @PostMapping("/todolist")
    public ResponseEntity<?> getTodoList(@RequestParam int id)
    {
          List<ToDoDto> todos = tdSrv.getTodoList(id);

          return new ResponseEntity( HttpStatus.OK);
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

