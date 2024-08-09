package com.todolist.app.exceptionHandler;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<CommonResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception)
    {
        CommonResponse response = new CommonResponse();

        response.info.code = StatusCode.NOt_Found;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidInputException.class)
     public ResponseEntity<CommonResponse> InvalidInputExceptionHandler(InvalidInputException exception)
    {
        CommonResponse response = new CommonResponse();

        response.info.code = StatusCode.Bad_Request;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ExceptionHandler(BadCredentialsException.class)
     public ResponseEntity<CommonResponse> BadCredentialsExceptionHandler(BadCredentialsException exception)
    {
        CommonResponse response = new CommonResponse();

        response.info.code = StatusCode.Bad_Request;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> ExcepitonHandler(Exception exception)
    {
        CommonResponse response = new CommonResponse();

        response.info.code = StatusCode.server_error;
        // Sorry for the joke because I am bored and can't think of anything else and kind a procastinating too
        response.info.message = "Well! bad luck because I also don't what happened there";
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
