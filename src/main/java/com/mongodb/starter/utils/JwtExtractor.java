package com.mongodb.starter.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Utility class to extract JWT tokens from request headers.
 * This is separate from the rest of the application so it doesn't affect existing code.
 */
@Component
public class JwtExtractor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Extract JWT token from request headers
     *
     * @param request HTTP request
     * @return JWT token or null if not found
     */
    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        
        return null;
    }

    /**
     * Check if request has a JWT token
     *
     * @param request HTTP request
     * @return true if request has a JWT token, false otherwise
     */
    public boolean hasToken(HttpServletRequest request) {
        return extractToken(request) != null;
    }
} 