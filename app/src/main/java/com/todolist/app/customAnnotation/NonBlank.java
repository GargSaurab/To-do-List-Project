package com.todolist.app.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.bv.NotBlankValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * Checks if the annotated string is not empty after trimming.
 */
@Constraint(validatedBy = NonBlankValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NonBlank {

    String message() default "Blank Value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
