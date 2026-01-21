package com.stfrancishyd.event_registration.service;

import com.stfrancishyd.event_registration.dto.EmailVerificationRequest;
import com.stfrancishyd.event_registration.dto.StudentProfileRequest;
import com.stfrancishyd.event_registration.dto.UserLoginRequest;
import com.stfrancishyd.event_registration.dto.UserRegisterRequest;
import com.stfrancishyd.event_registration.dto.UserResponse;

public interface UserService {
    
    // User registration - sends OTP to email
    UserResponse register(UserRegisterRequest request);
    
    // Verify email with OTP
    UserResponse verifyEmail(EmailVerificationRequest request);
    
    // User login - returns JWT token
    UserResponse login(UserLoginRequest request);
    
    // Update student profile with additional details
    UserResponse updateStudentProfile(String email, StudentProfileRequest request);
    
    // Resend verification OTP
    void resendVerificationOTP(String email);
    
    // Forgot password - sends reset OTP
    void forgotPassword(String email);
    
    // Reset password with OTP
    void resetPassword(String email, String otp, String newPassword);
}
