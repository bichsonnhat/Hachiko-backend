package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.UserNotificationDTO;
import com.mongodb.starter.usecases.interfaces.UserNotificationUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user-notifications")
@Tag(name = "user-notifications")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class UserNotificationController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserNotificationController.class);
    private final UserNotificationUsecase userNotificationUsecase;

    public UserNotificationController(UserNotificationUsecase userNotificationUsecase) {
        this.userNotificationUsecase = userNotificationUsecase;
    }

    @Operation(summary = "Get all user notifications", description = "Retrieves a list of all user notifications in the system", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all user notifications")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserNotificationDTO> getAllUserNotifications() {
        return userNotificationUsecase.getAllUserNotifications();
    }

    @Operation(summary = "Get user notification by ID", description = "Retrieves a user notification by its ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user notification"),
            @ApiResponse(responseCode = "404", description = "User notification not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserNotificationDTO> getUserNotificationById(@PathVariable String id) {
        return userNotificationUsecase.getUserNotificationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user notifications by user ID", description = "Retrieves all notifications for a specific user", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user notifications")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserNotificationDTO> getUserNotificationsByUserId(@PathVariable String userId) {
        return userNotificationUsecase.getUserNotificationsByUserId(userId);
    }
    
    @Operation(summary = "Get count of unseen notifications by user ID", description = "Retrieves the count of unseen notifications for a specific user", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved unseen notification count")
    @GetMapping("/user/{userId}/unseen-count")
    @ResponseStatus(HttpStatus.OK)
    public Long getUnseenNotificationsCountByUserId(@PathVariable String userId) {
        return userNotificationUsecase.countUnseenNotificationsByUserId(userId);
    }

    @Operation(summary = "Get user notifications by notification ID", description = "Retrieves all users who have a specific notification", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user notifications")
    @GetMapping("/notification/{notificationId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserNotificationDTO> getUserNotificationsByNotificationId(@PathVariable String notificationId) {
        return userNotificationUsecase.getUserNotificationsByNotificationId(notificationId);
    }

    @Operation(summary = "Create user notification", description = "Creates a new user notification", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "201", description = "Successfully created user notification")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserNotificationDTO createUserNotification(@RequestBody UserNotificationDTO userNotificationDTO) {
        return userNotificationUsecase.createUserNotification(userNotificationDTO);
    }

    @Operation(summary = "Update user notification", description = "Updates an existing user notification", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user notification"),
            @ApiResponse(responseCode = "404", description = "User notification not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserNotificationDTO> updateUserNotification(@PathVariable String id, @RequestBody UserNotificationDTO userNotificationDTO) {
        try {
            UserNotificationDTO updatedUserNotification = userNotificationUsecase.updateUserNotification(id, userNotificationDTO);
            return ResponseEntity.ok(updatedUserNotification);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error updating user notification: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete user notification", description = "Deletes a user notification by its ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the user notification"),
            @ApiResponse(responseCode = "404", description = "User notification not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserNotification(@PathVariable String id) {
        try {
            userNotificationUsecase.deleteUserNotification(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting user notification: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Mark user notification as seen", description = "Marks a specific user notification as seen", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully marked as seen"),
            @ApiResponse(responseCode = "404", description = "User notification not found")
    })
    @PutMapping("/{id}/seen")
    public ResponseEntity<UserNotificationDTO> markAsSeen(@PathVariable String id) {
        try {
            UserNotificationDTO updated = userNotificationUsecase.markNotificationAsSeen(id);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error marking notification as seen: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 