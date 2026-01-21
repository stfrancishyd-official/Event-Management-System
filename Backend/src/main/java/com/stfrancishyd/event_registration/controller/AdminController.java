package com.stfrancishyd.event_registration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stfrancishyd.event_registration.dto.EventResponse;
import com.stfrancishyd.event_registration.model.EventRegistration;
import com.stfrancishyd.event_registration.model.User;
import com.stfrancishyd.event_registration.repository.EventRegistrationRepository;
import com.stfrancishyd.event_registration.repository.UserRepository;
import com.stfrancishyd.event_registration.service.EventService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;
    
    // Get all events in the system
    @GetMapping("/events")
    public ResponseEntity<?> getAllEvents() {
        try {
            List<EventResponse> events = eventService.getAllEvents();
            return ResponseEntity.ok(events);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch events: " + e.getMessage());
        }
    }
    
    // Get all users in the system
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch users: " + e.getMessage());
        }
    }
    
    // Get all registrations in the system
    @GetMapping("/registrations")
    public ResponseEntity<?> getAllRegistrations() {
        try {
            List<EventRegistration> registrations = eventRegistrationRepository.findAll();
            return ResponseEntity.ok(registrations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch registrations: " + e.getMessage());
        }
    }
    
    // Get system statistics
    @GetMapping("/stats")
    public ResponseEntity<?> getSystemStats() {
        try {
            long totalUsers = userRepository.count();
            long totalEvents = eventService.getAllEvents().size();
            long totalRegistrations = eventRegistrationRepository.count();
            
            // Create a simple stats object
            var stats = new Object() {
                public final long totalUsers = AdminController.this.userRepository.count();
                public final long totalEvents = AdminController.this.eventService.getAllEvents().size();
                public final long totalRegistrations = AdminController.this.eventRegistrationRepository.count();
            };
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch statistics: " + e.getMessage());
        }
    }
}