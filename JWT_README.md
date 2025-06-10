# JWT Implementation

This project implements JWT (JSON Web Token) functionality for your Spring Boot application. The implementation is designed to work alongside your existing code without affecting it.

## Overview

JWT (JSON Web Token) is a compact, URL-safe means of representing claims to be transferred between two parties. This implementation provides utilities for generating, validating, and decrypting JWT tokens.

## Components

The JWT implementation consists of:

1. **Configuration**:
   - `JwtConfig`: Holds JWT configuration settings like secret key, expiration time, etc.
   - `JwtAuthConfig`: Configuration for the JWT authorization interceptor

2. **Utilities**:
   - `JwtUtils`: Core utility for JWT operations (signing, parsing, validation, etc.)
   - `JwtExtractor`: Utility to extract JWT tokens from HTTP request headers

3. **Service**:
   - `JwtService`: Business logic for JWT operations

4. **Controller**:
   - `JwtController`: REST endpoints for JWT operations

5. **Model**:
   - `JwtResponse`: Data Transfer Object for JWT token details

6. **Interceptors**:
   - `SimpleJwtAuthInterceptor`: A simple interceptor for JWT-based authorization

## Features

- **Token Generation**: Create JWT tokens with custom claims
- **Token Validation**: Verify token integrity and expiration
- **Token Decryption**: Extract claims from a token
- **Header Extraction**: Extract JWT tokens from Authorization headers
- **Role-Based Authorization**: Control access based on user roles in JWT claims

## Dependencies

The implementation requires the following dependencies:

```xml
<!-- JWT dependencies -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>${jjwt.version}</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>${jjwt.version}</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>${jjwt.version}</version>
    <scope>runtime</scope>
</dependency>
```

## Configuration

The JWT implementation uses the following configuration properties (set in application.properties or as environment variables):

```properties
# JWT Configuration (with defaults if not specified)
jwt.secret=defaultSecretKeyWhichIsAtLeast32CharactersLong
jwt.expiration=86400
jwt.issuer=hachiko-backend
jwt.audience=hachiko-users
```

## API Endpoints

The implementation provides the following REST endpoints:

1. **Generate Token**:
   - `POST /api/jwt/generate`
   - Request body: `{ "subject": "userId", "role": "admin", ... }`
   - Additional fields become JWT claims

2. **Validate Token**:
   - `POST /api/jwt/validate`
   - Request body: `{ "token": "eyJhbGciOiJIUzI1..." }`
   - Returns validation result and token details if valid

3. **Decrypt Token**:
   - `POST /api/jwt/decrypt`
   - Request body: `{ "token": "eyJhbGciOiJIUzI1..." }`
   - Returns token details including all claims

## Authorization Implementation

This implementation includes two options for JWT-based authorization:

### 1. Simple Authorization (SimpleJwtAuthInterceptor)

This interceptor provides straightforward role-based authorization using patterns defined in code:

- Roles and permissions are defined in the `SimpleJwtAuthInterceptor` class
- Each role has a list of URL patterns it can access
- URL patterns support wildcards (* for single path segment, ** for multiple)
- The interceptor is configured in `JwtAuthConfig`

To customize permissions:

1. Modify the `rolePermissions` map in `SimpleJwtAuthInterceptor`
2. Add/remove roles and their allowed URL patterns

### 2. Casbin Integration (JwtAuthorizationInterceptor)

If you're using Casbin, you can integrate JWT with it:

1. Extract the JWT token from the request
2. Validate the token and extract the role claim
3. Use Casbin to check if the role has permission for the requested resource

## Using the JWT Utilities in Your Code

To use the JWT utilities in your existing code, inject the relevant components:

```java
@Autowired
private JwtService jwtService;

@Autowired
private JwtExtractor jwtExtractor;

// Example: Extract and validate a token from a request
public boolean validateRequestToken(HttpServletRequest request) {
    String token = jwtExtractor.extractToken(request);
    if (token != null) {
        return jwtService.validateToken(token);
    }
    return false;
}

// Example: Get user ID from a token
public String getUserIdFromToken(String token) {
    return jwtService.getSubjectFromToken(token);
}
```

## Enabling/Disabling Authorization

To disable JWT authorization:

1. Open `src/main/java/com/mongodb/starter/config/JwtAuthConfig.java`
2. Comment out the `@Configuration` annotation
3. Rebuild and restart the application

## Security Considerations

1. **Secret Key**: Use a strong, unique secret key in production.
2. **Token Expiration**: Set an appropriate expiration time for tokens.
3. **HTTPS**: Always use HTTPS in production to protect tokens in transit.
4. **Claims**: Only include necessary information in token claims.

## Example Usage

```bash
# Generate a token
curl -X POST http://localhost:8080/api/jwt/generate \
  -H "Content-Type: application/json" \
  -d '{"subject":"user123","role":"admin","name":"John Doe"}'

# Validate a token
curl -X POST http://localhost:8080/api/jwt/validate \
  -H "Content-Type: application/json" \
  -d '{"token":"eyJhbGciOiJIUzI1..."}'

# Decrypt a token
curl -X POST http://localhost:8080/api/jwt/decrypt \
  -H "Content-Type: application/json" \
  -d '{"token":"eyJhbGciOiJIUzI1..."}'

# Use a token for authorization
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1..."
``` 