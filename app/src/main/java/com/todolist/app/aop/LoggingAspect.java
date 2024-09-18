package com.todolist.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before; //  slf4j ->  Simple Logging Facade for Java
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 *   Reference => https://www.baeldung.com/spring-aspect-oriented-programming-logging
*/

// The @Aspect annotation serves as a marker annotation, so Spring won’t automatically treat it as a component.
@Aspect
@Component

public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Pointcut that matches all public methods in all classes within the
     * com.todolist.app package.
     */
    @Pointcut("execution(public * com.todolist.app..*(..))")
    public void appMethods(){
    }

    /**
     * This AspectJ pointcut is responsible for logging before a method is called.
     *
     * @param joinPoint The JoinPoint which contains information about the method
     *                  that is being advised.
     */
    @Before(value = "appMethods()")
    public void logBefore(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodClass = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getName();
        String returnType = methodSignature.getReturnType().getSimpleName();
        Object[] methodArgs = joinPoint.getArgs();

        logger.info("Entering CLASS:{} METHOD:{} with ReturnType:{} and Arguments:{}", methodClass, methodName,returnType, methodArgs);
    }

    /**
     * This AspectJ pointcut is responsible for logging the result of a method
     * after it has finished executing.
     *
     * @param joinPoint The JoinPoint which contains information about the method
     *                  that is being advised.
     * @param result    The result of the method call.
     */
    @AfterReturning(value = "appMethods()", returning = "result")
    public void loggingAfterReturning(JoinPoint joinPoint, Object result) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodClass = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getName();

        logger.info("Exiting CLASS:{} METHOD:{} with result:{}", methodClass, methodName, result);
    }

    /**
     * This AspectJ pointcut is responsible for logging any exceptions that occur
     * during the execution of a method.
     *
     * @param joinPoint The JoinPoint which contains information about the method
     *                  that is being advised.
     * @param exception The exception that occurred during the execution of the
     *                  method.
     */
    @AfterThrowing(value = "appMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception){

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodClass = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getName();

        logger.error("Exception in CLASS:{} METHOD:{} with result:{}", methodClass, methodName, exception.getMessage());
    }
}
