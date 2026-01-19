package com.sushilk.quiz_app.user.service;

import com.sushilk.quiz_app.common.exception.ApiException;
import com.sushilk.quiz_app.user.dto.RegisterUserRequest;
import com.sushilk.quiz_app.user.entity.Role;
import com.sushilk.quiz_app.user.entity.User;
import com.sushilk.quiz_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(RegisterUserRequest request) {

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ApiException("User with email already exists: " + request.email(), HttpStatus.BAD_REQUEST);
        }

        Role role = Role.STUDENT; // default

        if (request.role() != null && request.role().equalsIgnoreCase("ADMIN")) {
            role = Role.ADMIN;
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .role(role)
                .build();

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateRole(Long userId, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.valueOf(role.toUpperCase()));
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}