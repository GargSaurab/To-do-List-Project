package com.todolist.app.util;

public interface StatusCode {
    int SUCCESS = 200;
    int CREATED = 201;
    int NO_CONTENT = 204;
    int BAD_REQUEST = 400;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int CONFLICT = 409;
    int DATA_EXPIRED = 410;
    int UNPROCESSABLE_ENTITY = 422;
    int SERVER_ERROR = 500;
    int SERVICE_UNAVAILABLE = 503; 
}

