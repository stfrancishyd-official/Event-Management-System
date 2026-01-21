package com.stfrancishyd.event_registration.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name; // User's full name
    
    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email is required")
    private String email; // User's email address
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password; // User's password
    
    private String department; // User's department (optional during registration)
    
    // Additional fields for students (optional during initial registration)
    private String rollNumber; // Student roll number
    private String section; // Student section
    private Integer batchYear; // Student batch year
}
