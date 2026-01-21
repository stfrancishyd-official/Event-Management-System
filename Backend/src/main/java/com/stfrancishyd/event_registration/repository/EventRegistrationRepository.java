package com.stfrancishyd.event_registration.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stfrancishyd.event_registration.model.Event;
import com.stfrancishyd.event_registration.model.EventRegistration;
import com.stfrancishyd.event_registration.model.User;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {
    
    // Check if a user is already registered for an event
    boolean existsByEventAndUser(Event event, User user);
    
    // Find registration by event and user
    Optional<EventRegistration> findByEventAndUser(Event event, User user);
    
    // Find all registrations for a specific event (for faculty to see who registered)
    List<EventRegistration> findByEventOrderByRegisteredAtAsc(Event event);
    
    // Find all registrations by a specific user (student's registration history)
    List<EventRegistration> findByUserOrderByRegisteredAtDesc(User user);
    
    // Count registrations for a specific event
    long countByEvent(Event event);
    
    // Get registration statistics for events created by a faculty member
    @Query("SELECT er FROM EventRegistration er WHERE er.event.createdBy = :faculty ORDER BY er.registeredAt DESC")
    List<EventRegistration> findRegistrationsByFaculty(@Param("faculty") User faculty);
}