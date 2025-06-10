package com.mongodb.starter.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mongodb.starter.services.AuthorizationService;
import com.mongodb.starter.services.JwtService;
import com.mongodb.starter.utils.JwtExtractor;

/**
 * Example interceptor that combines JWT authentication with Casbin authorization.
 * This is NOT automatically registered - it's an example of how to integrate JWT with Casbin.
 * To use this, you would need to register it in a configuration class.
 */
public class JwtAuthorizationInterceptor implements HandlerInterceptor {
    
    private final AuthorizationService authorizationService;
    private final JwtService jwtService;
    private final JwtExtractor jwtExtractor;
    
    public JwtAuthorizationInterceptor(
            AuthorizationService authorizationService,
            JwtService jwtService,
            JwtExtractor jwtExtractor) {
        this.authorizationService = authorizationService;
        this.jwtService = jwtService;
        this.jwtExtractor = jwtExtractor;
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
            String method = request.getMethod();
            
            // Check if the user has permission using Casbin
            boolean hasPermission = authorizationService.isAllowed(userRole, path, method);
            
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
} 