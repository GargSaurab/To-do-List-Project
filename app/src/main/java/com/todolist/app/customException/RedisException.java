package com.todolist.app.customException;

import lombok.Data;

@Data
public class RedisException extends RuntimeException{
 
    private int statusCode;
    private Exception exception;

    public RedisException(String message, int statusCode, Exception e) {
        super(message);
        this.statusCode = statusCode;
        this.exception = e;
    }

}
