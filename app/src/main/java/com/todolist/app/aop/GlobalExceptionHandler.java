package com.todolist.app.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.todolist.app.customException.EmailSendingException;
import com.todolist.app.customException.InvalidInputException;
import com.todolist.app.customException.RedisException;
import com.todolist.app.customException.ResourceNotFoundException;
import com.todolist.app.util.CommonResponse;
import com.todolist.app.util.LogUtil;
import com.todolist.app.util.StatusCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse> ResourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.NOT_FOUND;
        response.info.message = exception.getMessage();
        LogUtil.error(ResourceNotFoundException.class, "Resource not found: " + exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<CommonResponse> InvalidInputExceptionHandler(InvalidInputException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.BAD_REQUEST;
        response.info.message = exception.getMessage();
        LogUtil.error(InvalidInputException.class, "Invalid input: " + exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<CommonResponse> EmailSendingExceptionHandler(EmailSendingException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = exception.getStatusCode();
        response.info.message = exception.getMessage();
        LogUtil.error(EmailSendingException.class, "Email sending failed: " + exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CommonResponse> MethodArgumentNotValidExceptionHandler(
            MethodArgumentNotValidException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.UNPROCESSABLE_ENTITY;
        response.info.message = exception.getMessage();
        LogUtil.error(MethodArgumentNotValidException.class, "Method argument not valid: " + exception.getMessage(),
                exception);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CommonResponse> BadCredentialsExceptionHandler(BadCredentialsException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.BAD_REQUEST;
        response.info.message = exception.getMessage();
        LogUtil.error(BadCredentialsException.class, "Bad credentials::" + exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(RedisException.class)
    public ResponseEntity<CommonResponse> RedisExceptionHandler(RedisException exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = exception.getStatusCode();
        response.info.message = exception.getMessage();
        LogUtil.error(RedisException.class, "Error with Redis:" + exception.getMessage(), exception.getException());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse> ExcepitonHandler(Exception exception) {
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.SERVER_ERROR;
        // Sorry for the joke because I am bored and can't think of anything else and
        // kind a procastinating too
        response.info.message = "Well! bad luck because I also don't what happened there";
        LogUtil.error(Exception.class, "Unexpected error: " + exception.getMessage(), exception);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
