package com.todolist.app.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

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
