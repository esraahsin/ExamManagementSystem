package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;


import java.util.Map;
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
            user.setPassword(request.getPassword()); 
            user.setRole(role);
            user.setActive(true);
            user.setSpeciality(request.getSpeciality());
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
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid email or password"));
            }
    
            User user = userOptional.get();
    
            // Vérifier le mot de passe (⚠️ en production, utiliser un mot de passe hashé)
            if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.badRequest().body(Map.of("message", "Invalid email or password"));
            }

    
            // Simuler la génération d'un token (⚠️ Remplace avec un vrai JWT en prod)
            String token = "fake-jwt-token-" + user.getUserId();
    
            // Construire l'objet de réponse avec l'utilisateur et le token
            Map<String, Object> response = Map.of(
                "token", token,
                "user", Map.of(
                    "userId", user.getUserId(),
                    "email", user.getEmail(),
                    "role", user.getRole()
                )
            );
    
        

            // Successful login response (consider returning a JWT token for authentication)
            return ResponseEntity.ok(Map.of(
                "email", user.getEmail(),
                "role", user.getRole() // Make sure role is returned
            ));

        } catch (Exception e) {
            logger.error("Login error: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body(Map.of("message", "Login failed: " + e.getMessage()));
        }
    }}
    