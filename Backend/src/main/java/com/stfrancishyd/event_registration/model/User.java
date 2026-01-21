package com.stfrancishyd.event_registration.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; // Fixed: Added missing import
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter  // Lombok annotation to generate getter methods
@Setter  // Lombok annotation to generate setter methods
@NoArgsConstructor  // Lombok annotation to generate no-args constructor
@AllArgsConstructor // Lombok annotation to generate all-args constructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Fixed: Added GenerationType import
    private Long id;

    @Column(nullable = false)
    private String name; // User's full name

    @Column(nullable = false, unique = true)
    private String email; // User's email (must be unique)

    @Column(nullable = false)
    private String password; // Encrypted password

    @Enumerated(EnumType.STRING)
    private Role role; // User role: STUDENT, FACULTY, or ADMIN

    @Column(name = "is_verified") // Fixed: Better column naming
    private boolean isVerified = false; // Email verification status

    @Column(name = "verification_token")
    private String verificationToken; // OTP token for email verification

    @Column(name = "token_expiry")
    private LocalDateTime tokenExpiry; // Token expiration time

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); // Account creation timestamp

    // Additional fields for students
    @Column(name = "roll_number")
    private String rollNumber; // Student roll number (nullable for faculty/admin)

    private String section; // Student section (nullable for faculty/admin)

    @Column(name = "batch_year")
    private Integer batchYear; // Student batch year (nullable for faculty/admin)

    private String department; // User's department
}
