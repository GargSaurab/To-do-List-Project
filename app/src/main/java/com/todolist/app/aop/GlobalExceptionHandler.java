package com.todolist.app.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.StatusCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.NOT_FOUND;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<CommonResponse> InvalidInputExceptionHandler(InvalidInputException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.BAD_REQUEST;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> MethodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.UNPROCESSABLE_ENTITY;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse> BadCredentialsExceptionHandler(BadCredentialsException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.BAD_REQUEST;
        response.info.message = exception.getMessage();
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> ExcepitonHandler(Exception exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.SERVER_ERROR;
        // Sorry for the joke because I am bored and can't think of anything else and
        // kind a procastinating too
        response.info.message = "Well! bad luck because I also don't what happened there";
        exception.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
