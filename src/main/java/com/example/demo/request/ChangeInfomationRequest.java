package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInfomationRequest {
    @NotBlank(message = "firstname is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    public String firstname;

    @NotBlank(message = "lastname is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String lastname;
}
