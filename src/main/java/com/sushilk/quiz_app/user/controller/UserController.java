package com.sushilk.quiz_app.user.controller;

import com.sushilk.quiz_app.user.dto.RegisterUserRequest;
import com.sushilk.quiz_app.user.entity.User;
import com.sushilk.quiz_app.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    // PUBLIC: Register User / Admin
    @PostMapping("/register")
    public User register(@Valid @RequestBody RegisterUserRequest request) {
        return userService.register(request);
    }

    // ADMIN: Get all users
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ADMIN: Change role
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}/role")
    public User updateRole(@PathVariable Long userId,
                           @RequestParam String role) {
        return userService.updateRole(userId, role);
    }
}

