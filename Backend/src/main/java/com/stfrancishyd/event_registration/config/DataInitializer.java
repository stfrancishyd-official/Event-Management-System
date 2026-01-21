package com.stfrancishyd.event_registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.stfrancishyd.event_registration.model.Role;
import com.stfrancishyd.event_registration.model.User;
import com.stfrancishyd.event_registration.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin user if not exists
        if (!userRepository.existsByEmail("admin@stfrancis.edu")) {
            User admin = new User();
            admin.setName("System Administrator");
            admin.setEmail("admin@stfrancis.edu");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setVerified(true); // Admin is pre-verified
            admin.setDepartment("Administration");
            
            userRepository.save(admin);
            System.out.println("Default admin user created: admin@stfrancis.edu / admin123");
        }
        
        // Create default faculty user if not exists
        if (!userRepository.existsByEmail("faculty@stfrancis.edu")) {
            User faculty = new User();
            faculty.setName("Dr. Sample Faculty");
            faculty.setEmail("faculty@stfrancis.edu");
            faculty.setPassword(passwordEncoder.encode("faculty123"));
            faculty.setRole(Role.FACULTY);
            faculty.setVerified(true); // Faculty is pre-verified
            faculty.setDepartment("Computer Science");
            
            userRepository.save(faculty);
            System.out.println("Default faculty user created: faculty@stfrancis.edu / faculty123");
        }
    }
}