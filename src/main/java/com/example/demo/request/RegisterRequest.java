package com.example.demo.request;


import com.example.demo.validation.EmailNotExists;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest { ;

    @NotBlank(message = "firstname is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String firstname;

    @NotBlank(message = "lastname is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String lastname;

    @NotBlank(message = "email is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @EmailNotExists
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String password;

}
