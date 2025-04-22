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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.usecases.interfaces.UserUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "users")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class UserController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserUsecase userUsecase;

    public UserController(UserUsecase userUsecase) {
        this.userUsecase = userUsecase;
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all users")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        return userUsecase.getAllUsers();
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return userUsecase.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by email", description = "Retrieves a user by their email address", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
        return userUsecase.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user by phone number", description = "Retrieves a user by their phone number", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/phone")
    public ResponseEntity<UserDTO> getUserByPhoneNumber(@RequestParam String phoneNumber) {
        return userUsecase.getUserByPhoneNumber(phoneNumber)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create user", description = "Creates a new user. A unique ID must be provided in the request body.", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created user"),
            @ApiResponse(responseCode = "400", description = "Invalid request - ID is missing or invalid")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO createdUser = userUsecase.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Update user", description = "Updates an existing user", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userUsecase.updateUser(id, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error updating user: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete user", description = "Deletes a user by their ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the user"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        try {
            userUsecase.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting user: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 