package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.dto.*;
import com.nutriomedics.backend.entity.User;
import com.nutriomedics.backend.service.UserService;
import com.nutriomedics.backend.config.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        User user = userService.findByEmail(request.getEmail()).orElse(null);

        if (user == null || !userService.checkPassword(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body(new MessageResponse("Invalid email or password"));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return ResponseEntity.ok(new AuthResponse(
                token, user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }

    @PostMapping("/register-bootstrap")
    public ResponseEntity<?> createFirstAdmin(@Valid @RequestBody RegisterRequest request) {
        if (userService.countAdmins() > 0) {
            return ResponseEntity.status(403).body(new MessageResponse("Admin already exists"));
        }

        userService.registerAdmin(request.getName(), request.getEmail(), request.getPassword());

        return ResponseEntity.ok(new MessageResponse("First admin created successfully"));
    }
}