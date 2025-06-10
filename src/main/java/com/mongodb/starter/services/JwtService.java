package com.mongodb.starter.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.JwtResponse;
import com.mongodb.starter.utils.JwtUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for JWT operations.
 * This is separate from the rest of the application so it doesn't affect existing code.
 */
@Service
public class JwtService {

    private final JwtUtils jwtUtils;

    public JwtService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    /**
     * Generate a JWT token for a given subject
     *
     * @param subject User identifier
     * @return JWT response with token details
     */
    public JwtResponse generateToken(String subject) {
        return generateToken(subject, new HashMap<>());
    }

    /**
     * Generate a JWT token for a given subject with additional claims
     *
     * @param subject User identifier
     * @param claims Additional claims to include in the token
     * @return JWT response with token details
     */
    public JwtResponse generateToken(String subject, Map<String, Object> claims) {
        String token = jwtUtils.generateToken(subject, claims);
        
        Claims tokenClaims = jwtUtils.getAllClaimsFromToken(token);
        Date issuedAt = tokenClaims.getIssuedAt();
        Date expiresAt = tokenClaims.getExpiration();
        
        return new JwtResponse(token, subject, issuedAt, expiresAt, claims);
    }

    /**
     * Decrypt a JWT token and extract its details
     *
     * @param token JWT token
     * @return JWT response with token details
     * @throws ExpiredJwtException If the token is expired
     * @throws UnsupportedJwtException If the token is not supported
     * @throws MalformedJwtException If the token is malformed
     * @throws SignatureException If the token signature is invalid
     * @throws IllegalArgumentException If the token is invalid
     */
    public JwtResponse decryptToken(String token) throws ExpiredJwtException, UnsupportedJwtException,
            MalformedJwtException, SignatureException, IllegalArgumentException {
        Jws<Claims> jws = jwtUtils.parseToken(token);
        Claims claims = jws.getBody();
        
        String subject = claims.getSubject();
        Date issuedAt = claims.getIssuedAt();
        Date expiresAt = claims.getExpiration();
        
        // Extract custom claims (excluding standard JWT claims)
        Map<String, Object> customClaims = new HashMap<>(claims);
        customClaims.remove("sub");
        customClaims.remove("iat");
        customClaims.remove("exp");
        customClaims.remove("iss");
        customClaims.remove("aud");
        
        return new JwtResponse(token, subject, issuedAt, expiresAt, customClaims);
    }

    /**
     * Validate a JWT token
     *
     * @param token JWT token
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    /**
     * Extract subject from JWT token
     *
     * @param token JWT token
     * @return Subject
     */
    public String getSubjectFromToken(String token) {
        return jwtUtils.getSubjectFromToken(token);
    }

    /**
     * Check if token is expired
     *
     * @param token JWT token
     * @return true if expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return jwtUtils.isTokenExpired(token);
    }
    
    /**
     * Extract all claims from JWT token
     *
     * @param token JWT token
     * @return All claims
     */
    public Claims getAllClaimsFromToken(String token) {
        return jwtUtils.getAllClaimsFromToken(token);
    }
} 