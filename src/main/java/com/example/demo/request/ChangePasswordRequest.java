package com.example.demo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "currentPassword is required")
    @Size(min = 3, max = 20, message = "currentPassword must be between 3 and 20 characters")
    private String currentPassword;

    @NotBlank(message = "newPassword is required")
    @Size(min = 3, max = 20, message = "newPassword must be between 3 and 20 characters")
    private String newPassword;

    @NotBlank(message = "confirmationPassword is required")
    @Size(min = 3, max = 20, message = "confirmationPassword must be between 3 and 20 characters")
    private String confirmationPassword;
}