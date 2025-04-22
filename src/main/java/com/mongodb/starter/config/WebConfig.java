package com.mongodb.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.starter.interceptor.ApiKeyInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final ApiKeyConfig apiKeyConfig;

    public WebConfig(ApiKeyConfig apiKeyConfig) {
        this.apiKeyConfig = apiKeyConfig;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ApiKeyInterceptor(apiKeyConfig))
            .addPathPatterns("/**") // Apply to all endpoints
            .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**"); // Exclude Swagger UI if needed  
    }
}