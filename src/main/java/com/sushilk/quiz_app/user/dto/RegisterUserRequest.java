package com.sushilk.quiz_app.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @Email String email,
        @NotBlank String password,
        @NotBlank String name,
        String role   // USER / ADMIN (optional)
) {}

