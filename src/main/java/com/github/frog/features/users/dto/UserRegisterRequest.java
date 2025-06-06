package com.github.frog.features.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email must be a valid email address")
        String email,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 5, max = 75, message = "Password must be between {min} and {max} characters long")
        String password
) {
}

