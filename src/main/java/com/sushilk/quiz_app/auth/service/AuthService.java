package com.sushilk.quiz_app.auth.service;

import com.sushilk.quiz_app.auth.dto.AuthResponse;
import com.sushilk.quiz_app.auth.dto.LoginRequest;
import com.sushilk.quiz_app.auth.util.JwtUtil;
import com.sushilk.quiz_app.common.exception.ApiException;
import com.sushilk.quiz_app.user.entity.User;
import com.sushilk.quiz_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole()
        );

        return new AuthResponse(
                token,
                user.getEmail(),
                user.getRole().name()
        );
    }
}
