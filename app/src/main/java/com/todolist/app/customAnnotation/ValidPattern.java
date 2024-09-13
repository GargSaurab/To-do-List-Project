package com.todolist.app.customAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = PatternValidator.class)// Specifies the validator class
// Specifies who can use it .RUNTIME means both compiler and JVM can use it with the help of reflection
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.FIELD) // Specifies the element type on which we can use this annotation
public @interface ValidPattern {
 // As we know annotation is used to provice the metadata so write all the data that we want to provide
    String type();
    String message() default "Invalid format"; // default keyword will provide
    Class<?>[] groups() default {}; // This is where you define validation groups
    Class<? extends Payload>[] payload() default {};
}
