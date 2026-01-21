package com.stfrancishyd.event_registration.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stfrancishyd.event_registration.model.Event;
import com.stfrancishyd.event_registration.model.User;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    
    // Find all active events (for student view)
    List<Event> findByIsActiveTrueOrderByEventDateAsc();
    
    // Find events created by a specific faculty member
    List<Event> findByCreatedByOrderByCreatedAtDesc(User createdBy);
    
    // Find events by department
    List<Event> findByDepartmentOrderByEventDateAsc(String department);
    
    // Find upcoming events (event date is in the future)
    @Query("SELECT e FROM Event e WHERE e.eventDate > :currentDate AND e.isActive = true ORDER BY e.eventDate ASC")
    List<Event> findUpcomingEvents(@Param("currentDate") LocalDateTime currentDate);
    
    // Find events with available slots (current participants < max participants)
    @Query("SELECT e FROM Event e WHERE e.currentParticipants < e.maxParticipants AND e.isActive = true ORDER BY e.eventDate ASC")
    List<Event> findEventsWithAvailableSlots();
}