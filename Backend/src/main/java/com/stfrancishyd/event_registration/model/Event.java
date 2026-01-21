package com.stfrancishyd.event_registration.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique event identifier
    
    @Column(nullable = false)
    private String title; // Event title/name
    
    @Lob // Large object for storing long descriptions
    @Column(columnDefinition = "TEXT")
    private String description; // Detailed event description
    
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate; // Date and time when event will occur
    
    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline; // Last date for registration
    
    private String department; // Department conducting the event
    
    @Column(name = "poster_url")
    private String posterUrl; // URL/path to event poster image
    
    @Column(name = "max_participants")
    private Integer maxParticipants; // Maximum number of participants allowed
    
    @Column(name = "current_participants")
    private Integer currentParticipants = 0; // Current number of registered participants
    
    @Column(name = "is_active")
    private Boolean isActive = true; // Whether event is active for registration
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy; // Faculty who created this event
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); // Event creation timestamp
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now(); // Last update timestamp
    
    // List of all registrations for this event
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventRegistration> registrations;
}