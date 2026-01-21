package com.stfrancishyd.event_registration.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stfrancishyd.event_registration.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
