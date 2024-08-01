package com.todolist.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {

    @NotNull(message = "Username can't be null")
    private String username;
    @NotEmpty(message = "Email is required")
    @Email(message = "EmailId should be valid")
    private String emailId;
    @NotEmpty(message = "Password is required")
    @
    private String password;
}
