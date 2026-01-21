package com.stfrancishyd.event_registration.dto;

import java.time.LocalDateTime;
import com.stfrancishyd.event_registration.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id; // User's unique identifier
    private String name; // User's full name
    private String email; // User's email address
    private Role role; // User's role in the system
    private boolean isVerified; // Email verification status
    private LocalDateTime createdAt; // Account creation timestamp
    private String department; // User's department
    
    // Student-specific fields (null for faculty/admin)
    private String rollNumber; // Student roll number
    private String section; // Student section
    private Integer batchYear; // Student batch year
    
    private String token; // JWT token for authentication (included in login response)
}
