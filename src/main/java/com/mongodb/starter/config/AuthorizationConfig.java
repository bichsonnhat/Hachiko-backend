package com.mongodb.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.starter.interceptor.AuthorizationInterceptor;
import com.mongodb.starter.services.AuthorizationService;

/**
 * This configuration is separate from the main WebConfig to avoid
 * affecting the existing application behavior.
 * It can be enabled or disabled by commenting out the @Configuration annotation.
 */
@Configuration
@Order(2) // Ensure this runs after the main WebConfig
public class AuthorizationConfig implements WebMvcConfigurer {
    
    private final AuthorizationService authorizationService;
    
    public AuthorizationConfig(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Comment out the following line to disable Casbin authorization
        registry.addInterceptor(new AuthorizationInterceptor(authorizationService))
                .addPathPatterns("/api/**") // Apply only to API endpoints
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**"); // Exclude Swagger UI
    }
} 