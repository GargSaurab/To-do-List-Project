package com.todolist.app.customAnnotation;

public class CustomeValidator implements ConstraintValidator<ValidPattern, String> {

    @Value("${user.validation.emailIdPattern}")
     private String emailIdPattern;
     
     @Value("${user.validation.passwordPattern}")
     private String passwordPattern;

     private String pattern;

     @Override
     public void initilalize(ValidPattern constraintAnnotation)
     {
         switch(constraintAnnotation.type())
         {
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
     public boolean isValid(String value, ConstraintVaidator)

}
