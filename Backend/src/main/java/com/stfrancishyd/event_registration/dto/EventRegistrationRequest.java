package com.stfrancishyd.event_registration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventRegistrationRequest {
    
    @NotNull(message = "Event ID is required")
    private Long eventId; // ID of the event to register for
    
    @NotBlank(message = "Student name is required")
    private String studentName; // Student's full name
    
    @NotBlank(message = "Roll number is required")
    private String rollNumber; // Student's roll number
    
    @NotBlank(message = "Section is required")
    private String section; // Student's section
    
    @NotNull(message = "Batch year is required")
    private Integer batchYear; // Student's batch year
}