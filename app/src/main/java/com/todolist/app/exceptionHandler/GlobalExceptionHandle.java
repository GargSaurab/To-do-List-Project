package com.todolist.app.exceptionHandler;

import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<CommonResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception)
    {
        CommonResponse response = new CommonResponse();

        response.info.code = StatusCode.NOt_Found;
        response.info.message = exception.getMessage();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> ExcepitonHandler(Exception exception)
    {
        CommonResponse response = new CommonResponse();

        response.info.code = StatusCode.server_error;
        response.info.message = exception.getMessage();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
