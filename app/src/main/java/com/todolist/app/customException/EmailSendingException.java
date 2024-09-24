package com.todolist.app.customException;

import lombok.Getter;

@Getter
public class EmailSendingException extends RuntimeException{

    private final int statusCode;

     public EmailSendingException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
