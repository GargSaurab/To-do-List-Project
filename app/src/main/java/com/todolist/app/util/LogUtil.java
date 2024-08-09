package com.todolist.app.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

    private LogUtil()
    {
        throw new RuntimeException("You shouldn't have instantiated it");
    }

    public static void trace(Class className, String message)
    {
         Logger logger = LoggerFactory.getLogger(className);
         logger.trace(message);
    }
    public static void debug(Class className, String message)
    {
         Logger logger = LoggerFactory.getLogger(className);
         logger.debug(message);
    }
    public static void info(Class className, String message)
    {
         Logger logger = LoggerFactory.getLogger(className);
         logger.info(message);
    }
    public static void warn(Class className, String message)
    {
         Logger logger = LoggerFactory.getLogger(className);
         logger.warn(message);
    }
    public static void error(Class className, String message)
    {
         Logger logger = LoggerFactory.getLogger(className);
         logger.error(message);
    }

}
