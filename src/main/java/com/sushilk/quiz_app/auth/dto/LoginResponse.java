package com.sushilk.quiz_app.auth.dto;

public record LoginResponse(String token, String email, String role) {}
