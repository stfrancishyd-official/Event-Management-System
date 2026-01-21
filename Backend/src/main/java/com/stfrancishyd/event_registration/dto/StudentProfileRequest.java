package com.stfrancishyd.event_registration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileRequest {
    
    @NotBlank(message = "Name is required")
    private String name; // Student's full name
    
    @NotBlank(message = "Roll number is required")
    private String rollNumber; // Student's roll number
    
    @NotBlank(message = "Section is required")
    private String section; // Student's section (e.g., A, B, C)
    
    @NotNull(message = "Batch year is required")
    private Integer batchYear; // Student's batch year (e.g., 2024, 2025)
    
    private String department; // Student's department
}