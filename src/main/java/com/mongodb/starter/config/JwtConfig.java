package com.mongodb.starter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for JWT settings.
 * This is separate from the rest of the application so it doesn't affect existing code.
 */
@Configuration
public class JwtConfig {

    @Value("${jwt.secret:defaultSecretKeyWhichIsAtLeast32CharactersLong}")
    private String secret;

    @Value("${jwt.expiration:86400}") // Default: 24 hours in seconds
    private long expiration;

    @Value("${jwt.issuer:hachiko-backend}")
    private String issuer;

    @Value("${jwt.audience:hachiko-users}")
    private String audience;

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getAudience() {
        return audience;
    }
} 