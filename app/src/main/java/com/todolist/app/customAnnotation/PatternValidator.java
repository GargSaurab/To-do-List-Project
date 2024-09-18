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

     /**
      * Initialize the validator with the pattern to match against.
      * @param constraintAnnotation the annotation to initialize with
      */
     @Override
     public void initialize(ValidPattern constraintAnnotation)
     {
         // Get the pattern from a properties file based on the type of pattern
         switch(constraintAnnotation.type())
         {
             case "username":
              // Username should be alphanumeric and should contain at least 3 and at most 15 characters
              this.pattern = usernamePattern;
              break;

             case "emailId":
             // EmailId should contain the '@' symbol and should contain at least 3 and at most 20 characters after the '@'
             this.pattern = emailIdPattern;
             break;

            case "password":
             // Password should be alphanumeric and should contain at least 8 and at most 20 characters
             this.pattern = passwordPattern;
             break;

             default:
              throw new IllegalArgumentException("Wrong pattern type");
         }
       }

     /**
      * Checks if the supplied {@code value} matches the regular expression
      * from the properties file.
      *
      * @param value the value to be checked
      * @param context the context in which the constraint is evaluated
      * @return {@code true} if the value matches the regular expression,
      *         {@code false} otherwise
      */
     @Override
     public boolean isValid(String value, ConstraintValidatorContext  context)
     {
       // Also checks if value is null or empty
        if(value == null || value.trim().isEmpty()){
              return false;
        }
        // The matches() method of the String class takes a regular expression as
        // an argument and returns true if the string matches the regular expression
        return value.matches(pattern);
     }

}
