# Casbin Authorization Implementation

This folder contains the configuration files for Casbin-based authorization in the application.

## Overview

Casbin is a powerful and efficient open-source access control library. This implementation adds role-based access control to the application without affecting the existing code.

## Files

- `model.conf`: Defines the RBAC model with domain
- `policy.csv`: Contains access control policies

## How It Works

1. The system uses a role-based access control model where:
   - Users can have roles
   - Roles have permissions to access resources with specific actions
   - Permissions are defined as: `subject, resource, action`

2. The authorization flow:
   - A request comes in with a user role (via header `X-USER-ROLE`)
   - The AuthorizationInterceptor extracts the role and the requested resource and action
   - The Casbin enforcer checks if the role has permission for the resource/action
   - If permitted, the request continues; otherwise, a 403 Forbidden response is returned

## Testing

You can test the authorization using the `/api/casbin` endpoints:

- `GET /api/casbin/info`: Get request information
- `GET /api/casbin/check/{role}/{resource}/{action}`: Check if a role has permission
- `GET /api/casbin/roles/{user}`: Get roles for a user
- `POST /api/casbin/add-role/{user}/{role}`: Add a role to a user

## Enabling/Disabling

To disable the Casbin authorization:

1. Comment out the `@Configuration` annotation in `AuthorizationConfig.java`
2. Or comment out the interceptor registration in the `addInterceptors` method

## Example Usage

Testing with curl:

```bash
# Test with user role
curl -H "X-USER-ROLE: user" http://localhost:8080/api/casbin/info

# Test with admin role
curl -H "X-USER-ROLE: admin" http://localhost:8080/api/casbin/info

# Check if admin can access all products
curl http://localhost:8080/api/casbin/check/admin/api/products/GET

# Check if user can delete a product (should be false)
curl http://localhost:8080/api/casbin/check/user/api/products/123/DELETE
``` 