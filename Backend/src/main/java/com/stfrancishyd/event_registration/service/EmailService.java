package com.stfrancishyd.event_registration.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    // Email configuration from application properties
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    // Generate 6-digit OTP
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate 6-digit number
        return String.valueOf(otp);
    }
    
    // Send OTP email for account verification
    public void sendVerificationEmail(String toEmail, String otp, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Email Verification - Event Registration System");
            
            String emailBody = String.format(
                "Dear %s,\n\n" +
                "Thank you for registering with our Event Registration System!\n\n" +
                "Your email verification code is: %s\n\n" +
                "This code will expire in 15 minutes. Please enter this code to verify your email address.\n\n" +
                "If you didn't create an account, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Event Registration Team\n" +
                "St. Francis College",
                userName, otp
            );
            
            message.setText(emailBody);
            mailSender.send(message);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to send verification email: " + e.getMessage());
        }
    }
    
    // Send password reset email
    public void sendPasswordResetEmail(String toEmail, String resetToken, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Password Reset - Event Registration System");
            
            String emailBody = String.format(
                "Dear %s,\n\n" +
                "You have requested to reset your password for the Event Registration System.\n\n" +
                "Your password reset code is: %s\n\n" +
                "This code will expire in 15 minutes. Please use this code to reset your password.\n\n" +
                "If you didn't request a password reset, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Event Registration Team\n" +
                "St. Francis College",
                userName, resetToken
            );
            
            message.setText(emailBody);
            mailSender.send(message);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to send password reset email: " + e.getMessage());
        }
    }
    
    // Send event registration confirmation email
    public void sendEventRegistrationConfirmation(String toEmail, String userName, String eventTitle, String eventDate) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("Event Registration Confirmation - " + eventTitle);
            
            String emailBody = String.format(
                "Dear %s,\n\n" +
                "You have successfully registered for the following event:\n\n" +
                "Event: %s\n" +
                "Date: %s\n\n" +
                "Please make sure to attend the event on time.\n\n" +
                "Best regards,\n" +
                "Event Registration Team\n" +
                "St. Francis College",
                userName, eventTitle, eventDate
            );
            
            message.setText(emailBody);
            mailSender.send(message);
            
        } catch (Exception e) {
            // Log error but don't throw exception to avoid breaking registration process
            System.err.println("Failed to send registration confirmation email: " + e.getMessage());
        }
    }
}