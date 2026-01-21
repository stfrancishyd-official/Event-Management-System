package com.stfrancishyd.event_registration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailVerificationRequest {
    
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email; // User's email address
    
    @NotBlank(message = "OTP is required")
    private String otp; // 6-digit OTP code sent to user's email
}