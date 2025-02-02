package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            // Convert role to enum
            UserRole role;
            try {
                role = request.getRole();
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role: " + request.getRole());
            }

            // Check email exists
            if (userRepository.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // Check user ID exists
            if (userRepository.findByUserId(request.getUserIdd()) != null) {
                return ResponseEntity.badRequest().body("User ID already exists");
            }

            // Create user
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword()); // Use BCrypt for password hashing in production
            user.setRole(role);
            user.setActive(true);
            user.setSpecialty(request.getSpecialty());
            user.setUserId(request.getUserIdd());

            userRepository.save(user);

            return ResponseEntity.ok("Registration successful");

        } catch (Exception e) {
            logger.error("Registration error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }

            User user = userOptional.get();

            // Validate password (in production, use hashed password comparison)
            if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }

            // Successful login response (consider returning a JWT token for authentication)
            return ResponseEntity.ok("Login successful");

        } catch (Exception e) {
            logger.error("Login error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Login failed: " + e.getMessage());
        }
    }
}
