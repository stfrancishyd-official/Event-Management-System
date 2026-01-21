package com.stfrancishyd.event_registration.service;

import java.util.List;

import com.stfrancishyd.event_registration.dto.EventCreateRequest;
import com.stfrancishyd.event_registration.dto.EventRegistrationRequest;
import com.stfrancishyd.event_registration.dto.EventResponse;
import com.stfrancishyd.event_registration.model.EventRegistration;

public interface EventService {
    
    // Create new event (Faculty only)
    EventResponse createEvent(String facultyEmail, EventCreateRequest request);
    
    // Get all active events (for students)
    List<EventResponse> getAllActiveEvents(String studentEmail);
    
    // Get events created by faculty
    List<EventResponse> getEventsByFaculty(String facultyEmail);
    
    // Get event by ID
    EventResponse getEventById(Long eventId, String userEmail);
    
    // Update event (Faculty only - can only update their own events)
    EventResponse updateEvent(Long eventId, String facultyEmail, EventCreateRequest request);
    
    // Delete event (Faculty only - can only delete their own events)
    void deleteEvent(Long eventId, String facultyEmail);
    
    // Register student for event
    String registerForEvent(String studentEmail, EventRegistrationRequest request);
    
    // Get registrations for an event (Faculty only - for their events)
    List<EventRegistration> getEventRegistrations(Long eventId, String facultyEmail);
    
    // Get student's registration history
    List<EventResponse> getStudentRegistrations(String studentEmail);
    
    // Get all events (Admin only)
    List<EventResponse> getAllEvents();
    
    // Get registration statistics for faculty
    List<EventRegistration> getFacultyRegistrationStats(String facultyEmail);
}