package com.todolist.app.util;

import com.todolist.app.dto.ToDoRequest;
import com.todolist.app.service.TodoServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;

public class Utility {

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
}
