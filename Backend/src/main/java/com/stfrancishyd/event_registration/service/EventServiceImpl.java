package com.stfrancishyd.event_registration.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stfrancishyd.event_registration.dto.EventCreateRequest;
import com.stfrancishyd.event_registration.dto.EventRegistrationRequest;
import com.stfrancishyd.event_registration.dto.EventResponse;
import com.stfrancishyd.event_registration.model.Event;
import com.stfrancishyd.event_registration.model.EventRegistration;
import com.stfrancishyd.event_registration.model.Role;
import com.stfrancishyd.event_registration.model.User;
import com.stfrancishyd.event_registration.repository.EventRegistrationRepository;
import com.stfrancishyd.event_registration.repository.EventRepository;
import com.stfrancishyd.event_registration.repository.UserRepository;

@Service
@Transactional
public class EventServiceImpl implements EventService {
    
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;
    
    @Autowired
    private EmailService emailService;
    
    // Create new event (Faculty only)
    @Override
    public EventResponse createEvent(String facultyEmail, EventCreateRequest request) {
        // Find faculty user
        User faculty = userRepository.findByEmail(facultyEmail)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        // Verify user is faculty
        if (faculty.getRole() != Role.FACULTY) {
            throw new RuntimeException("Only faculty members can create events");
        }
        
        // Create new event
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setRegistrationDeadline(request.getRegistrationDeadline());
        event.setDepartment(request.getDepartment());
        event.setPosterUrl(request.getPosterUrl());
        event.setMaxParticipants(request.getMaxParticipants());
        event.setCreatedBy(faculty);
        event.setUpdatedAt(LocalDateTime.now());
        
        // Save event
        event = eventRepository.save(event);
        
        return convertToEventResponse(event, null);
    }
    
    // Get all active events (for students)
    @Override
    public List<EventResponse> getAllActiveEvents(String studentEmail) {
        // Find student user
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Get all active events
        List<Event> events = eventRepository.findByIsActiveTrueOrderByEventDateAsc();
        
        // Convert to response DTOs and check if student is registered
        return events.stream()
                .map(event -> {
                    boolean isRegistered = eventRegistrationRepository.existsByEventAndUser(event, student);
                    EventResponse response = convertToEventResponse(event, null);
                    response.setIsRegistered(isRegistered);
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    // Get events created by faculty
    @Override
    public List<EventResponse> getEventsByFaculty(String facultyEmail) {
        // Find faculty user
        User faculty = userRepository.findByEmail(facultyEmail)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        // Get events created by this faculty
        List<Event> events = eventRepository.findByCreatedByOrderByCreatedAtDesc(faculty);
        
        return events.stream()
                .map(event -> convertToEventResponse(event, null))
                .collect(Collectors.toList());
    }
    
    // Get event by ID
    @Override
    public EventResponse getEventById(Long eventId, String userEmail) {
        // Find event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Find user
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if student is registered (for student users)
        Boolean isRegistered = null;
        if (user.getRole() == Role.STUDENT) {
            isRegistered = eventRegistrationRepository.existsByEventAndUser(event, user);
        }
        
        EventResponse response = convertToEventResponse(event, null);
        response.setIsRegistered(isRegistered);
        return response;
    }
    
    // Update event (Faculty only - can only update their own events)
    @Override
    public EventResponse updateEvent(Long eventId, String facultyEmail, EventCreateRequest request) {
        // Find faculty user
        User faculty = userRepository.findByEmail(facultyEmail)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        // Find event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Check if faculty owns this event
        if (!event.getCreatedBy().getId().equals(faculty.getId())) {
            throw new RuntimeException("You can only update your own events");
        }
        
        // Update event details
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setRegistrationDeadline(request.getRegistrationDeadline());
        event.setDepartment(request.getDepartment());
        event.setPosterUrl(request.getPosterUrl());
        event.setMaxParticipants(request.getMaxParticipants());
        event.setUpdatedAt(LocalDateTime.now());
        
        // Save updated event
        event = eventRepository.save(event);
        
        return convertToEventResponse(event, null);
    }
    
    // Delete event (Faculty only - can only delete their own events)
    @Override
    public void deleteEvent(Long eventId, String facultyEmail) {
        // Find faculty user
        User faculty = userRepository.findByEmail(facultyEmail)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        // Find event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Check if faculty owns this event
        if (!event.getCreatedBy().getId().equals(faculty.getId())) {
            throw new RuntimeException("You can only delete your own events");
        }
        
        // Delete event (this will also delete related registrations due to cascade)
        eventRepository.delete(event);
    }
    
    // Register student for event
    @Override
    public String registerForEvent(String studentEmail, EventRegistrationRequest request) {
        // Find student user
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Verify user is student
        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException("Only students can register for events");
        }
        
        // Find event
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Check if event is active
        if (!event.getIsActive()) {
            throw new RuntimeException("Event registration is closed");
        }
        
        // Check if registration deadline has passed
        if (event.getRegistrationDeadline() != null && 
            LocalDateTime.now().isAfter(event.getRegistrationDeadline())) {
            throw new RuntimeException("Registration deadline has passed");
        }
        
        // Check if student is already registered
        if (eventRegistrationRepository.existsByEventAndUser(event, student)) {
            throw new RuntimeException("You are already registered for this event");
        }
        
        // Check if event has available slots
        if (event.getCurrentParticipants() >= event.getMaxParticipants()) {
            throw new RuntimeException("Event is full. No more registrations allowed");
        }
        
        // Create registration
        EventRegistration registration = new EventRegistration();
        registration.setEvent(event);
        registration.setUser(student);
        registration.setStudentName(request.getStudentName());
        registration.setRollNumber(request.getRollNumber());
        registration.setSection(request.getSection());
        registration.setBatchYear(request.getBatchYear());
        
        // Save registration
        eventRegistrationRepository.save(registration);
        
        // Update event participant count
        event.setCurrentParticipants(event.getCurrentParticipants() + 1);
        eventRepository.save(event);
        
        // Send confirmation email
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        emailService.sendEventRegistrationConfirmation(
            student.getEmail(),
            student.getName(),
            event.getTitle(),
            event.getEventDate().format(formatter)
        );
        
        return "Successfully registered for the event!";
    }
    
    // Get registrations for an event (Faculty only - for their events)
    @Override
    public List<EventRegistration> getEventRegistrations(Long eventId, String facultyEmail) {
        // Find faculty user
        User faculty = userRepository.findByEmail(facultyEmail)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        // Find event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        // Check if faculty owns this event
        if (!event.getCreatedBy().getId().equals(faculty.getId())) {
            throw new RuntimeException("You can only view registrations for your own events");
        }
        
        return eventRegistrationRepository.findByEventOrderByRegisteredAtAsc(event);
    }
    
    // Get student's registration history
    @Override
    public List<EventResponse> getStudentRegistrations(String studentEmail) {
        // Find student user
        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        
        // Get student's registrations
        List<EventRegistration> registrations = eventRegistrationRepository.findByUserOrderByRegisteredAtDesc(student);
        
        // Convert to event responses
        return registrations.stream()
                .map(registration -> {
                    EventResponse response = convertToEventResponse(registration.getEvent(), null);
                    response.setIsRegistered(true);
                    return response;
                })
                .collect(Collectors.toList());
    }
    
    // Get all events (Admin only)
    @Override
    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(event -> convertToEventResponse(event, null))
                .collect(Collectors.toList());
    }
    
    // Get registration statistics for faculty
    @Override
    public List<EventRegistration> getFacultyRegistrationStats(String facultyEmail) {
        // Find faculty user
        User faculty = userRepository.findByEmail(facultyEmail)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        return eventRegistrationRepository.findRegistrationsByFaculty(faculty);
    }
    
    // Helper method to convert Event entity to EventResponse DTO
    private EventResponse convertToEventResponse(Event event, Boolean isRegistered) {
        EventResponse response = new EventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setEventDate(event.getEventDate());
        response.setRegistrationDeadline(event.getRegistrationDeadline());
        response.setDepartment(event.getDepartment());
        response.setPosterUrl(event.getPosterUrl());
        response.setMaxParticipants(event.getMaxParticipants());
        response.setCurrentParticipants(event.getCurrentParticipants());
        response.setIsActive(event.getIsActive());
        response.setCreatedByName(event.getCreatedBy().getName());
        response.setCreatedAt(event.getCreatedAt());
        response.setIsRegistered(isRegistered);
        return response;
    }
}