package com.todolist.app.util;

import com.todolist.app.dto.CommonResponse;
import com.todolist.app.dto.StatusCode;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;

public class Utility {

    // Used as a measure for endDate/time it will be handled at frontend but still for safety is
    // checked
    public static boolean isDateTimeValid(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime){
        if(endDate != null && startDate != null)
        {
            if(endDate.isBefore(startDate)){
                return false;
            }
            if(endDate.equals(startDate) && endTime != null && startTime != null){
                return endTime.isAfter(startTime);
            }
        }
        return true;
    }

    public static CommonResponse success(String message){
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.SUCCESS;
        response.info.message = message;
        return response;
    }

    public static CommonResponse success(String message, Object data){
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.SUCCESS;
        response.info.message = message;
        response.data = data;
        return response;
    }

    public static CommonResponse error(String message){
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.SERVER_ERROR;
        response.info.message = "Error";
        if(!isEmpty(message)){
            response.info.message = message;
        }else{
            response.info.message = "Error";
        }
        return response;
    }

    public static boolean isEmpty(Object input) {
        if (input == null) {
            return true;
        }
        // Check for String
        if (input instanceof String) {
            return ((String) input).isEmpty();
        }
        // Check for Array
        if (input.getClass().isArray()) {
            return java.lang.reflect.Array.getLength(input) == 0;
        }
        // Check for Collection (List, Set, etc.)
        if (input instanceof Collection) {
            return ((Collection<?>) input).isEmpty();
        }
        // Check for Map
        if (input instanceof Map) {
            return ((Map<?, ?>) input).isEmpty();
        }
        // For any other type, we assume it's not "empty" if it's non-null
        return false;
    }
}
