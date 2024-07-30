package com.todolist.app.customException;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException (String message)
    {
        super(message);
    }
}
