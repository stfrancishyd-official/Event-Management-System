package com.stfrancishyd.event_registration.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_registrations", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "user_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Unique registration identifier
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event; // The event being registered for
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The student who registered
    
    @Column(name = "student_name", nullable = false)
    private String studentName; // Student's name at time of registration
    
    @Column(name = "roll_number", nullable = false)
    private String rollNumber; // Student's roll number
    
    @Column(nullable = false)
    private String section; // Student's section
    
    @Column(name = "batch_year", nullable = false)
    private Integer batchYear; // Student's batch year
    
    @Column(name = "registered_at")
    private LocalDateTime registeredAt = LocalDateTime.now(); // Registration timestamp
}