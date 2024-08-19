package com.todolist.app.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class PatternValidator implements ConstraintValidator<ValidPattern, String> {

    @Value("${user.validation.usernamePattern}")
     private String usernamePattern;

     @Value("${user.validation.emailIdPattern}")
     private String emailIdPattern;

     @Value("${user.validation.passwordPattern}")
     private String passwordPattern;


     private String pattern;

     @Override
     public void initialize(ValidPattern constraintAnnotation)
     {
         switch(constraintAnnotation.type())
         {
             case "username":
              this.pattern = usernamePattern;
              break;

             case "emailId":
             this.pattern = emailIdPattern;
             break;

            case "password":
             this.pattern = passwordPattern;
             break;

             default:
              throw new IllegalArgumentException("Wrong pattern type");

         }
       }

     @Override
     public boolean isValid(String value, ConstraintValidatorContext  context)
     {
       // Also checks if value is null or empty
        if(value == null || value.trim().isEmpty()){
              return false;
        }

        return value.matches(pattern);
     }

}
