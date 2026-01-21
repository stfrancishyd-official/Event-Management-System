package com.stfrancishyd.event_registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stfrancishyd.event_registration.dto.EventCreateRequest;
import com.stfrancishyd.event_registration.dto.EventResponse;
import com.stfrancishyd.event_registration.model.EventRegistration;
import com.stfrancishyd.event_registration.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/faculty")
@CrossOrigin(origins = "*")
public class FacultyController {
    
    @Autowired
    private EventService eventService;
    
    // Create new event
    @PostMapping("/events")
    public ResponseEntity<?> createEvent(
            @Valid @RequestBody EventCreateRequest request,
            Authentication authentication) {
        try {
            String facultyEmail = authentication.getName();
            EventResponse event = eventService.createEvent(facultyEmail, request);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to create event: " + e.getMessage());
        }
    }
    
    // Get events created by this faculty
    @GetMapping("/my-events")
    public ResponseEntity<?> getMyEvents(Authentication authentication) {
        try {
            String facultyEmail = authentication.getName();
            List<EventResponse> events = eventService.getEventsByFaculty(facultyEmail);
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch events: " + e.getMessage());
        }
    }
    
    // Update event (only own events)
    @PutMapping("/events/{eventId}")
    public ResponseEntity<?> updateEvent(
            @PathVariable Long eventId,
            @Valid @RequestBody EventCreateRequest request,
            Authentication authentication) {
        try {
            String facultyEmail = authentication.getName();
            EventResponse event = eventService.updateEvent(eventId, facultyEmail, request);
            return ResponseEntity.ok(event);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update event: " + e.getMessage());
        }
    }
    
    // Delete event (only own events)
    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long eventId, Authentication authentication) {
        try {
            String facultyEmail = authentication.getName();
            eventService.deleteEvent(eventId, facultyEmail);
            return ResponseEntity.ok("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete event: " + e.getMessage());
        }
    }
    
    // Get registrations for a specific event (only own events)
    @GetMapping("/events/{eventId}/registrations")
    public ResponseEntity<?> getEventRegistrations(
            @PathVariable Long eventId,
            Authentication authentication) {
        try {
            String facultyEmail = authentication.getName();
            List<EventRegistration> registrations = eventService.getEventRegistrations(eventId, facultyEmail);
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch registrations: " + e.getMessage());
        }
    }
    
    // Get all registration statistics for this faculty
    @GetMapping("/registration-stats")
    public ResponseEntity<?> getRegistrationStats(Authentication authentication) {
        try {
            String facultyEmail = authentication.getName();
            List<EventRegistration> stats = eventService.getFacultyRegistrationStats(facultyEmail);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch statistics: " + e.getMessage());
        }
    }
}