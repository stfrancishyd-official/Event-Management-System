package com.stfrancishyd.event_registration.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventCreateRequest {
    
    @NotBlank(message = "Event title is required")
    private String title; // Event title/name
    
    @NotBlank(message = "Event description is required")
    private String description; // Detailed event description
    
    @NotNull(message = "Event date is required")
    @Future(message = "Event date must be in the future")
    private LocalDateTime eventDate; // Date and time when event will occur
    
    private LocalDateTime registrationDeadline; // Optional registration deadline
    
    @NotBlank(message = "Department is required")
    private String department; // Department conducting the event
    
    private String posterUrl; // Optional URL/path to event poster image
    
    @Positive(message = "Maximum participants must be positive")
    private Integer maxParticipants; // Maximum number of participants allowed
}