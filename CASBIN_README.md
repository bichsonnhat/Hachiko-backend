# Casbin Authorization Implementation

This project implements Casbin-based authorization for your Spring Boot application. The implementation is designed to work alongside your existing code without affecting it.

## What is Casbin?

Casbin is an authorization library that supports various access control models. This implementation uses a Role-Based Access Control (RBAC) model with resource patterns.

## Implementation Overview

The Casbin implementation consists of:

1. **Configuration Files**:
   - `model.conf`: Defines the RBAC model
   - `policy.csv`: Contains access control policies

2. **Java Classes**:
   - `CasbinConfig`: Bean configuration for Casbin enforcer
   - `AuthorizationService`: Service to check permissions
   - `AuthorizationInterceptor`: Intercepts HTTP requests to check permissions
   - `AuthorizationConfig`: Web configuration for the interceptor
   - `CasbinTestController`: Test endpoints for Casbin functionality

## Design Considerations

- **Non-intrusive**: The implementation is designed to work alongside your existing code without affecting it.
- **Easily Disabled**: The authorization can be disabled by commenting out the `@Configuration` annotation on `AuthorizationConfig`.
- **Separate Concerns**: The Casbin configuration is separate from your application's existing security.

## How to Use

### Basic Usage

1. Add the `X-USER-ROLE` header to your requests with the role of the user (e.g., "admin", "user", "store_owner").
2. The authorization will be applied automatically to all `/api/**` endpoints.

### Customizing Policies

Edit the `src/main/resources/casbin/policy.csv` file to define:
- Permissions for roles: `p, role, resource, action`
- Role inheritance: `g, user, role`

Example:
```
p, admin, /api/products/*, *
p, user, /api/products/*, GET
g, john, admin
```

### Testing

The implementation includes a test controller at `/api/casbin/` with endpoints to:
- Check permissions
- View user roles
- Add roles to users
- Get request information

## Integration with Existing Authentication

To integrate with your existing authentication system:

1. Modify the `extractUserRole` method in `AuthorizationInterceptor` to extract the user role from your authentication system (e.g., JWT token).

2. Update your role structure to match Casbin's expectations.

## Disabling the Authorization

If you need to disable the Casbin authorization:

1. Open `src/main/java/com/mongodb/starter/config/AuthorizationConfig.java`
2. Comment out the `@Configuration` annotation
3. Rebuild and restart the application

## Advanced Customization

For more complex authorization scenarios:

- Modify the `model.conf` file to define a different access control model
- Implement a database adapter instead of the file adapter for dynamic policy management
- Add domain support for multi-tenancy

## References

- [Casbin Documentation](https://casbin.org/docs/overview)
- [jCasbin GitHub Repository](https://github.com/casbin/jcasbin)
- [Casbin Spring Boot Starter](https://github.com/jcasbin/casbin-spring-boot-starter) 