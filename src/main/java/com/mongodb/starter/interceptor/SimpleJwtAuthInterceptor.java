package com.mongodb.starter.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mongodb.starter.services.JwtService;
import com.mongodb.starter.utils.JwtExtractor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple JWT authorization interceptor that doesn't rely on Casbin.
 * This can be used as an alternative if Casbin integration is problematic.
 */
public class SimpleJwtAuthInterceptor implements HandlerInterceptor {
    
    private final JwtService jwtService;
    private final JwtExtractor jwtExtractor;
    
    // Map of role to list of allowed endpoint patterns
    private final Map<String, List<String>> rolePermissions = new HashMap<>();
    
    public SimpleJwtAuthInterceptor(JwtService jwtService, JwtExtractor jwtExtractor) {
        this.jwtService = jwtService;
        this.jwtExtractor = jwtExtractor;
        
        // Initialize permissions - this could be loaded from a configuration file or database
        rolePermissions.put("admin", Collections.singletonList("/**"));
        rolePermissions.put("user", Arrays.asList(
            "/products/**", 
            "/categories/**", 
            "/orders/**", 
            "/store/**", 
            "/reviews/**", 
            "/notifications/**", 
            "/advertisements/**", 
            "/feedback/**", 
            "/favourite-products/**", 
            "/user-vouchers/**", 
            "/users/**"
        ));
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Extract the JWT token from the request
        String token = jwtExtractor.extractToken(request);
        
        // If no token is found, deny access
        if (token == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Access denied: No JWT token found");
            return false;
        }
        
        // Validate the token
        if (!jwtService.validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Access denied: Invalid JWT token");
            return false;
        }
        
        try {
            // Extract user role from token claims
            Claims claims = jwtService.getAllClaimsFromToken(token);
            String userRole = (String) claims.get("role");
            
            // If no role is found, deny access
            if (userRole == null || userRole.isEmpty()) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Access denied: No role found in JWT token");
                return false;
            }
            
            String path = request.getRequestURI();
            
            // Check if the user has permission
            boolean hasPermission = checkPermission(userRole, path);
            
            if (!hasPermission) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                response.getWriter().write("Access denied: Insufficient permissions");
                return false;
            }
            
            // Store the user ID in a request attribute for use in controllers
            String userId = jwtService.getSubjectFromToken(token);
            request.setAttribute("userId", userId);
            
            return true;
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Access denied: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if a user with the given role has permission to access the given path
     */
    private boolean checkPermission(String role, String path) {
        // If the role is not found, deny access
        if (!rolePermissions.containsKey(role)) {
            return false;
        }
        
        // Get the list of allowed patterns for the role
        List<String> allowedPatterns = rolePermissions.get(role);
        
        // Check if the path matches any of the allowed patterns
        for (String pattern : allowedPatterns) {
            if (matchesPattern(pattern, path)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Check if a path matches a pattern
     * Supports * wildcard
     */
    private boolean matchesPattern(String pattern, String path) {
        // Replace ** with a special marker
        String processedPattern = pattern.replace("**", "__DOUBLE_WILDCARD__");
        
        // Replace * with [^/]* (match anything except path separator)
        processedPattern = processedPattern.replace("*", "[^/]*");
        
        // Replace the special marker with .* (match anything including path separator)
        processedPattern = processedPattern.replace("__DOUBLE_WILDCARD__", ".*");
        
        // Add ^ and $ to match the entire string
        processedPattern = "^" + processedPattern + "$";
        
        // Replace / with \/ for the regex
        processedPattern = processedPattern.replace("/", "\\/");
        
        // Check if the path matches the pattern
        return path.matches(processedPattern);
    }
} 