package com.app.Controller;

import com.app.Dto.ToDoDto;
import com.app.Entity.ToDo;
import com.app.service.TodoService;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todolist")
public class ToDoController {

    @Autowired
    private TodoService tdSrv;

    @PostMapping("/todolist")
    public ResponseEntity<?> getTodoList(@RequestParam int id)
    {
          List<ToDoDto> todos = tdSrv.getTodoList(id);

          return new ResponseEntity( HttpStatus.OK);
    }

}
