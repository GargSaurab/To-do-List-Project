package com.todolist.app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.todolist.app.model.ToDoDto;
import com.todolist.app.model.ToDoRequest;
import com.todolist.app.service.TodoService;
import com.todolist.app.util.CommonResponse;
import com.todolist.app.util.CustomResponse;
import com.todolist.app.util.Utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor // for constructor injection makes the injection confirmed
public class ToDoController {

    private final TodoService tdSrv; // makes the class immutable which helps in
    // thread safety, security and simplicity.
    // Constructor Injection also allows it.

    /**
     * Returns the list of todos for the current user
     * @param request the HttpServletRequest
     * @return a ResponseEntity containing a CommonResponse with a list of ToDoDto
     */
    @GetMapping("/todolist")
    public ResponseEntity<CommonResponse> getTodoList(HttpServletRequest request)
    {
        List<ToDoDto> todos = tdSrv.getTodoList(Utility.getUserId(request));
        CommonResponse response = Utility.success("Successfull", todos);
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a new todo to the list
     * @param toDoRequest the new todo
     * @param request the HttpServletRequest
     * @return a ResponseEntity containing a CommonResponse with a success message
     */
    @PostMapping("/addTodo")
    public ResponseEntity<CommonResponse> addTodo(@Valid @RequestBody ToDoRequest toDoRequest, HttpServletRequest request)
    {
        // Extract the user id from the JWT token
        toDoRequest.setUserId(Utility.getUserId(request));
        tdSrv.addToDo(toDoRequest);
        CommonResponse response = Utility.success("Todo is saved", null);
        return ResponseEntity.ok(response);
    }

    /**
     * Removes a todo from the list
     * @param id the id of the todo to remove
     * @return a ResponseEntity containing a CommonResponse with a success message
     */
    @PostMapping("/removeTodo")
    public ResponseEntity<CustomResponse> removeTodo(@RequestParam int id)
    {
        tdSrv.removeTodo(id);
        CustomResponse response = Utility.success("Todo is removed");
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a todo in the list
     * @param toDoDto the new todo
     * @return a ResponseEntity containing a CommonResponse with a success message
     */
    @PostMapping("/updateTodo")
    public ResponseEntity<CustomResponse> updateTodo(@Valid @RequestBody ToDoDto toDoDto)
    {
        tdSrv.updateTodo(toDoDto);
        CustomResponse response = Utility.success( "Todo is updated");
        return ResponseEntity.ok(response);
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

