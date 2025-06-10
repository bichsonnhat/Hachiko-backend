package com.mongodb.starter.dtos;

import java.util.Date;
import java.util.Map;

/**
 * Data Transfer Object for JWT token response.
 */
public class JwtResponse {
    
    private String token;
    private String subject;
    private Date issuedAt;
    private Date expiresAt;
    private Map<String, Object> claims;
    
    public JwtResponse() {
    }
    
    public JwtResponse(String token, String subject, Date issuedAt, Date expiresAt, Map<String, Object> claims) {
        this.token = token;
        this.subject = subject;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.claims = claims;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Map<String, Object> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, Object> claims) {
        this.claims = claims;
    }
} 