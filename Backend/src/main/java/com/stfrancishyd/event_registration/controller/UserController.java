package com.stfrancishyd.event_registration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stfrancishyd.event_registration.dto.EmailVerificationRequest;
import com.stfrancishyd.event_registration.dto.StudentProfileRequest;
import com.stfrancishyd.event_registration.dto.UserLoginRequest;
import com.stfrancishyd.event_registration.dto.UserRegisterRequest;
import com.stfrancishyd.event_registration.dto.UserResponse;
import com.stfrancishyd.event_registration.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Allow requests from React frontend
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // User registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
        try {
            UserResponse response = userService.register(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
    
    // Email verification endpoint
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@Valid @RequestBody EmailVerificationRequest request) {
        try {
            UserResponse response = userService.verifyEmail(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email verification failed: " + e.getMessage());
        }
    }
    
    // User login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request) {
        try {
            UserResponse response = userService.login(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }
    
    // Resend verification OTP endpoint
    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOTP(@RequestParam String email) {
        try {
            userService.resendVerificationOTP(email);
            return ResponseEntity.ok("Verification OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }
    
    // Forgot password endpoint
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok("Password reset OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send reset OTP: " + e.getMessage());
        }
    }
    
    // Reset password endpoint
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {
        try {
            userService.resetPassword(email, otp, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Password reset failed: " + e.getMessage());
        }
    }
    
    // Update student profile endpoint (requires authentication)
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @Valid @RequestBody StudentProfileRequest request,
            Authentication authentication) {
        try {
            String email = authentication.getName(); // Get email from JWT token
            UserResponse response = userService.updateStudentProfile(email, request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Profile update failed: " + e.getMessage());
        }
    }
}
