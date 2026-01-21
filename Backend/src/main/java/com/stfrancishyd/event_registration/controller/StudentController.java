package com.stfrancishyd.event_registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stfrancishyd.event_registration.dto.EventRegistrationRequest;
import com.stfrancishyd.event_registration.dto.EventResponse;
import com.stfrancishyd.event_registration.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/student")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private EventService eventService;
    
    // Get all active events for students to view
    @GetMapping("/events")
    public ResponseEntity<?> getAllEvents(Authentication authentication) {
        try {
            String studentEmail = authentication.getName();
            List<EventResponse> events = eventService.getAllActiveEvents(studentEmail);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch events: " + e.getMessage());
        }
    }
    
    // Get specific event details
    @GetMapping("/events/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Long eventId, Authentication authentication) {
        try {
            String studentEmail = authentication.getName();
            EventResponse event = eventService.getEventById(eventId, studentEmail);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch event: " + e.getMessage());
        }
    }
    
    // Register for an event
    @PostMapping("/register")
    public ResponseEntity<?> registerForEvent(
            @Valid @RequestBody EventRegistrationRequest request,
            Authentication authentication) {
        try {
            String studentEmail = authentication.getName();
            String message = eventService.registerForEvent(studentEmail, request);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
    
    // Get student's registration history
    @GetMapping("/my-registrations")
    public ResponseEntity<?> getMyRegistrations(Authentication authentication) {
        try {
            String studentEmail = authentication.getName();
            List<EventResponse> registrations = eventService.getStudentRegistrations(studentEmail);
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch registrations: " + e.getMessage());
        }
    }
}