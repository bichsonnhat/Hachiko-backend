package com.mongodb.starter.services;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorizationService {

    private final Enforcer enforcer;

    public AuthorizationService(Enforcer enforcer) {
        this.enforcer = enforcer;
    }

    /**
     * Checks if a user has permission to access a resource with a given action
     * 
     * @param subject The user (or role)
     * @param resource The resource being accessed
     * @param action The action being performed (GET, POST, PUT, DELETE, etc.)
     * @return true if the user has permission, false otherwise
     */
    public boolean isAllowed(String subject, String resource, String action) {
        return enforcer.enforce(subject, resource, action);
    }
    
    /**
     * Adds a role for a user
     * 
     * @param user The user
     * @param role The role to add
     */
    public void addRoleForUser(String user, String role) {
        enforcer.addGroupingPolicy(user, role);
    }
    
    /**
     * Adds a permission
     * 
     * @param role The role
     * @param resource The resource
     * @param action The action
     */
    public void addPolicy(String role, String resource, String action) {
        enforcer.addPolicy(role, resource, action);
    }
    
    /**
     * Gets all roles for a user
     * 
     * @param user The user
     * @return List of roles
     */
    public List<String> getRolesForUser(String user) {
        return enforcer.getRolesForUser(user);
    }
} 