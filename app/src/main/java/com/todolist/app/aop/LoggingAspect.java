package com.todolist.app.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger; //  slf4j ->  Simple Logging Facade for Java
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
    Reference => https://www.baeldung.com/spring-aspect-oriented-programming-logging
*/

// The @Aspect annotation serves as a marker annotation, so Spring won’t automatically treat it as a component.
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(public * com.todolist.app..*(..))")
    public void appMethods(){
    }

    @Before(value = "appMethods()")
    public void logBefore(JoinPoint joinPoint) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodClass = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getName();
        String returnType = methodSignature.getReturnType().getSimpleName();
        Object[] methodArgs = joinPoint.getArgs();

         logger.info("Entering CLASS:{} METHOD:{} with ReturnType:{} and Arguments:{}", methodClass, methodName,returnType, methodArgs);
    }


    @AfterReturning(value = "appMethods()", returning = "result")
    public  void loggingAfterReturning(JoinPoint joinPoint, Object result) {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodClass = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getName();

        logger.info("Exiting CLASS:{} METHOD:{} with result:{}", methodClass, methodName, result);
    }

    @AfterThrowing(value = "appMethods()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception){

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String methodClass = methodSignature.getDeclaringTypeName();
        String methodName = methodSignature.getName();

        logger.error("Exception in CLASS:{} METHOD:{} with result:{}", methodClass, methodName, exception.getMessage());
    }
}
