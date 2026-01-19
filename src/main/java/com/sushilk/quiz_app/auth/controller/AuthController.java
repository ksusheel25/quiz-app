package com.sushilk.quiz_app.auth.controller;

import com.sushilk.quiz_app.auth.dto.AuthResponse;
import com.sushilk.quiz_app.auth.dto.LoginRequest;
import com.sushilk.quiz_app.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}


