package com.mongodb.starter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mongodb.starter.services.AuthorizationService;

public class AuthorizationInterceptor implements HandlerInterceptor {
    
    private final AuthorizationService authorizationService;
    
    public AuthorizationInterceptor(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // This is a placeholder. In a real application, you would extract the user role 
        // from the authenticated user (e.g., from JWT token, session, etc.)
        String userRole = extractUserRole(request);
        
        // If no role is found, deny access
        if (userRole == null) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied: No role found");
            return false;
        }
        
        String path = request.getRequestURI();
        String method = request.getMethod();
        
        // Check if the user has permission
        boolean hasPermission = authorizationService.isAllowed(userRole, path, method);
        
        if (!hasPermission) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.getWriter().write("Access denied: Insufficient permissions");
            return false;
        }
        
        return true;
    }
    
    /**
     * Extract the user role from the request
     * This is a placeholder method - in a real application, you would implement
     * this based on your authentication mechanism (JWT, session, etc.)
     * 
     * @param request The HTTP request
     * @return The user role or null if not found
     */
    private String extractUserRole(HttpServletRequest request) {
        // For demo purposes, we'll use a header. In a real app, you'd extract this
        // from the authenticated user information (e.g., JWT claims)
        String roleHeader = request.getHeader("X-USER-ROLE");
        
        if (roleHeader != null && !roleHeader.isEmpty()) {
            return roleHeader;
        }
        
        // For testing - if no header is present, assume "user" role for now
        // In a real app, you would return null or throw an exception
        return "user";
    }
} 