package com.mongodb.starter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mongodb.starter.config.ApiKeyConfig;

public class ApiKeyInterceptor implements HandlerInterceptor {
    private final ApiKeyConfig apiKeyConfig;

    public ApiKeyInterceptor(ApiKeyConfig apiKeyConfig) {
        this.apiKeyConfig = apiKeyConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("X-API-KEY");
        
        if (apiKey == null || !apiKey.equals(apiKeyConfig.getApiKey())) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Invalid or missing API key");
            return false;
        }
        
        return true;
    }
}