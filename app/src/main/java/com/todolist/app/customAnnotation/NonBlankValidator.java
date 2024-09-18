package com.todolist.app.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * This is the validator for the NonBlank annotation. The NonBlank annotation
 * is a custom annotation that will throw an exception if the annotated field
 * is blank after trimming.
 * @author aniket
 */
public class NonBlankValidator implements ConstraintValidator<NonBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // Since null is allowed it will pass the null
        if(value == null)
        {
            return true;
        }
        return !value.trim().isEmpty();
    }
}
