package com.todolist.app.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.todolist.app.entity.User;
import com.todolist.app.entity.UserCustomDetails;

import jakarta.servlet.http.HttpServletRequest;

public class Utility {

    @Autowired
    private static JwtHelper jwtHelper;

    @Autowired
    private static UserDetailsService userDetailsService;

    public static UUID getUserId(HttpServletRequest request){
        return UUID.fromString(jwtHelper.extractId(request.getHeader("Authorization")));
    }

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

    public static String getUsernameByEmail(String email){
         UserDetails userDetails = userDetailsService.loadUserByUsername(email);
          if(userDetails instanceof UserCustomDetails customDetails)
         {
             User user = customDetails.getUser();
             return user.getUsername();
         }else {
             throw new IllegalArgumentException("Unsupported UserDetails implementation");
         }
    }

    public static CustomResponse success(String message){
        CustomResponse response = new CustomResponse();
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

    public static CommonResponse error(String message, int code){
        CommonResponse response = new CommonResponse();
        if(!isEmpty(code)){
            response.info.code = code;
        }else{
            response.info.code = StatusCode.SERVER_ERROR;
        }
        if(!isEmpty(message)){
            response.info.message = message;
        }else{
            response.info.message = "Error";
        }
        return response;
    }

    public static CommonResponse error(String message, int code, Object data){
        CommonResponse response = new CommonResponse();
        response.info.code = StatusCode.SERVER_ERROR;
        response.data = data;
        if(!isEmpty(code)){
            response.info.code = code;
        }else{
            response.info.code = StatusCode.SERVER_ERROR;
        }
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
