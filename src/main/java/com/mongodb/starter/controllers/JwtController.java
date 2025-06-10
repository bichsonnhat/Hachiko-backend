package com.mongodb.starter.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.JwtResponse;
import com.mongodb.starter.services.JwtService;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for JWT operations.
 * This is separate from the rest of the application so it doesn't affect existing code.
 */
@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    private final JwtService jwtService;

    public JwtController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * Decrypt a JWT token
     *
     * @param request Map containing the token
     * @return JWT response with token details
     */
    @PostMapping("/decrypt")
    public ResponseEntity<?> decryptToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        
        if (token == null || token.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        try {
            JwtResponse jwtResponse = jwtService.decryptToken(token);
            return ResponseEntity.ok(jwtResponse);
        } catch (ExpiredJwtException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Token has expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid token: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Generate a JWT token for testing
     *
     * @param request Map containing the subject and claims
     * @return JWT response with token details
     */
    @PostMapping("/generate")
    public ResponseEntity<?> generateToken(@RequestBody Map<String, Object> request) {
        String subject = (String) request.get("subject");
        
        if (subject == null || subject.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Subject is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        // Extract claims from the request
        Map<String, Object> claims = new HashMap<>();
        request.forEach((key, value) -> {
            if (!key.equals("subject")) {
                claims.put(key, value);
            }
        });
        
        JwtResponse jwtResponse = jwtService.generateToken(subject, claims);
        return ResponseEntity.ok(jwtResponse);
    }
    
    /**
     * Validate a JWT token
     *
     * @param request Map containing the token
     * @return Map with validation result
     */
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        
        if (token == null || token.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Token is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        boolean isValid = jwtService.validateToken(token);
        
        Map<String, Object> response = new HashMap<>();
        response.put("valid", isValid);
        
        if (isValid) {
            try {
                String subject = jwtService.getSubjectFromToken(token);
                boolean isExpired = jwtService.isTokenExpired(token);
                
                response.put("subject", subject);
                response.put("expired", isExpired);
            } catch (Exception e) {
                // Ignore and return just the validation result
            }
        }
        
        return ResponseEntity.ok(response);
    }
} 