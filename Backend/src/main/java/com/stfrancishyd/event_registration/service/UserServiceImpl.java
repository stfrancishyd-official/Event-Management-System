package com.stfrancishyd.event_registration.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stfrancishyd.event_registration.dto.EmailVerificationRequest;
import com.stfrancishyd.event_registration.dto.StudentProfileRequest;
import com.stfrancishyd.event_registration.dto.UserLoginRequest;
import com.stfrancishyd.event_registration.dto.UserRegisterRequest;
import com.stfrancishyd.event_registration.dto.UserResponse;
import com.stfrancishyd.event_registration.model.Role;
import com.stfrancishyd.event_registration.model.User;
import com.stfrancishyd.event_registration.repository.UserRepository;
import com.stfrancishyd.event_registration.security.CustomUserDetailsService;
import com.stfrancishyd.event_registration.security.JwtUtil;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
    // Register new user and send verification email
    @Override
    public UserResponse register(UserRegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setRole(Role.STUDENT); // Default role is STUDENT
        user.setDepartment(request.getDepartment());
        user.setRollNumber(request.getRollNumber());
        user.setSection(request.getSection());
        user.setBatchYear(request.getBatchYear());
        user.setVerified(false); // Email not verified yet
        
        // Generate OTP and set expiry (15 minutes)
        String otp = emailService.generateOTP();
        user.setVerificationToken(otp);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        
        // Save user to database
        user = userRepository.save(user);
        
        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), otp, user.getName());
        
        // Return user response (without sensitive data)
        return convertToUserResponse(user, null);
    }
    
    // Verify email with OTP
    @Override
    public UserResponse verifyEmail(EmailVerificationRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if already verified
        if (user.isVerified()) {
            throw new RuntimeException("Email already verified");
        }
        
        // Check if OTP matches and is not expired
        if (!request.getOtp().equals(user.getVerificationToken())) {
            throw new RuntimeException("Invalid OTP");
        }
        
        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired. Please request a new one");
        }
        
        // Mark user as verified and clear verification token
        user.setVerified(true);
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        
        // Save updated user
        user = userRepository.save(user);
        
        // Generate JWT token for automatic login after verification
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        
        return convertToUserResponse(user, token);
    }
    
    // User login
    @Override
    public UserResponse login(UserLoginRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        // Check if email is verified
        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your email before logging in");
        }
        
        // Authenticate user
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        
        // Generate JWT token
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails);
        
        return convertToUserResponse(user, token);
    }
    
    // Update student profile with additional details
    @Override
    public UserResponse updateStudentProfile(String email, StudentProfileRequest request) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update user details
        user.setName(request.getName());
        user.setRollNumber(request.getRollNumber());
        user.setSection(request.getSection());
        user.setBatchYear(request.getBatchYear());
        user.setDepartment(request.getDepartment());
        
        // Save updated user
        user = userRepository.save(user);
        
        return convertToUserResponse(user, null);
    }
    
    // Resend verification OTP
    @Override
    public void resendVerificationOTP(String email) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if already verified
        if (user.isVerified()) {
            throw new RuntimeException("Email already verified");
        }
        
        // Generate new OTP and set expiry
        String otp = emailService.generateOTP();
        user.setVerificationToken(otp);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        
        // Save user
        userRepository.save(user);
        
        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), otp, user.getName());
    }
    
    // Forgot password - send reset OTP
    @Override
    public void forgotPassword(String email) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Generate reset token and set expiry
        String resetToken = emailService.generateOTP();
        user.setVerificationToken(resetToken);
        user.setTokenExpiry(LocalDateTime.now().plusMinutes(15));
        
        // Save user
        userRepository.save(user);
        
        // Send password reset email
        emailService.sendPasswordResetEmail(user.getEmail(), resetToken, user.getName());
    }
    
    // Reset password with OTP
    @Override
    public void resetPassword(String email, String otp, String newPassword) {
        // Find user by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check if OTP matches and is not expired
        if (!otp.equals(user.getVerificationToken())) {
            throw new RuntimeException("Invalid OTP");
        }
        
        if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired. Please request a new one");
        }
        
        // Update password and clear reset token
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setVerificationToken(null);
        user.setTokenExpiry(null);
        
        // Save user
        userRepository.save(user);
    }
    
    // Helper method to convert User entity to UserResponse DTO
    private UserResponse convertToUserResponse(User user, String token) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setVerified(user.isVerified());
        response.setCreatedAt(user.getCreatedAt());
        response.setDepartment(user.getDepartment());
        response.setRollNumber(user.getRollNumber());
        response.setSection(user.getSection());
        response.setBatchYear(user.getBatchYear());
        response.setToken(token);
        return response;
    }
}
