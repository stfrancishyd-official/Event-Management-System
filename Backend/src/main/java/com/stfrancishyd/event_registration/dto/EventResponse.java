package com.stfrancishyd.event_registration.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    
    private Long id; // Unique event identifier
    private String title; // Event title/name
    private String description; // Event description
    private LocalDateTime eventDate; // Event date and time
    private LocalDateTime registrationDeadline; // Registration deadline
    private String department; // Department conducting the event
    private String posterUrl; // Event poster URL
    private Integer maxParticipants; // Maximum participants allowed
    private Integer currentParticipants; // Current registered participants
    private Boolean isActive; // Whether event is active
    private String createdByName; // Name of faculty who created the event
    private LocalDateTime createdAt; // Event creation timestamp
    private Boolean isRegistered; // Whether current user is registered (for student view)
}