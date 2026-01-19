package com.sushilk.quiz_app.auth.dto;

public record AuthResponse(
        String token,
        String email,
        String role
) {}
