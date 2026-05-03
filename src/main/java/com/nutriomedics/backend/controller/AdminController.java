package com.nutriomedics.backend.controller;

import com.nutriomedics.backend.dto.*;
import com.nutriomedics.backend.entity.User;
import com.nutriomedics.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        User user = userService.getAdminProfile(auth.getName());
        return ResponseEntity.ok(new ProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getRole()));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest request, Authentication auth) {
        userService.updateAdminProfile(auth.getName(), request);
        return ResponseEntity.ok(new MessageResponse("Profile updated successfully"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request, Authentication auth) {
        userService.changePassword(auth.getName(), request);
        return ResponseEntity.ok(new MessageResponse("Password changed successfully"));
    }
}