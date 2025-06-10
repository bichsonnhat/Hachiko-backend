package com.mongodb.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.starter.interceptor.SimpleJwtAuthInterceptor;
import com.mongodb.starter.services.JwtService;
import com.mongodb.starter.utils.JwtExtractor;

/**
 * Configuration for JWT authentication and authorization.
 * This is separate from the main security configuration to avoid affecting existing code.
 * To disable JWT authorization, comment out the @Configuration annotation.
 */
@Configuration
@Order(3) // Ensure this runs after other security configurations
public class JwtAuthConfig implements WebMvcConfigurer {
    
    private final JwtService jwtService;
    private final JwtExtractor jwtExtractor;
    
    public JwtAuthConfig(JwtService jwtService, JwtExtractor jwtExtractor) {
        this.jwtService = jwtService;
        this.jwtExtractor = jwtExtractor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Comment out the following line to disable JWT authorization
        registry.addInterceptor(new SimpleJwtAuthInterceptor(jwtService, jwtExtractor))
                .addPathPatterns("/api/**") // Apply only to API endpoints
                .excludePathPatterns(
                    "/api/jwt/**",           // Exclude JWT endpoints for token generation and validation
                    "/swagger-ui/**",        // Exclude Swagger UI
                    "/v3/api-docs/**"        // Exclude OpenAPI docs
                );
    }
} 