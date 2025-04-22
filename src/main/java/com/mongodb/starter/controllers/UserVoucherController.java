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

import com.mongodb.starter.dtos.UserVoucherDTO;
import com.mongodb.starter.usecases.interfaces.UserVoucherUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user-vouchers")
@Tag(name = "user-vouchers")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class UserVoucherController {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserVoucherController.class);
    private final UserVoucherUsecase userVoucherUsecase;

    public UserVoucherController(UserVoucherUsecase userVoucherUsecase) {
        this.userVoucherUsecase = userVoucherUsecase;
    }

    @Operation(summary = "Get all user vouchers", description = "Retrieves a list of all user vouchers in the system", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all user vouchers")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserVoucherDTO> getAllUserVouchers() {
        return userVoucherUsecase.getAllUserVouchers();
    }

    @Operation(summary = "Get user voucher by ID", description = "Retrieves a user voucher by its ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the user voucher"),
            @ApiResponse(responseCode = "404", description = "User voucher not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserVoucherDTO> getUserVoucherById(@PathVariable String id) {
        return userVoucherUsecase.getUserVoucherById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get user vouchers by user ID", description = "Retrieves all vouchers for a specific user", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user vouchers")
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserVoucherDTO> getUserVouchersByUserId(@PathVariable String userId) {
        return userVoucherUsecase.getUserVouchersByUserId(userId);
    }

    @Operation(summary = "Get user vouchers by voucher ID", description = "Retrieves all users who have a specific voucher", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user vouchers")
    @GetMapping("/voucher/{voucherId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserVoucherDTO> getUserVouchersByVoucherId(@PathVariable String voucherId) {
        return userVoucherUsecase.getUserVouchersByVoucherId(voucherId);
    }

    @Operation(summary = "Create user voucher", description = "Creates a new user voucher", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "201", description = "Successfully created user voucher")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserVoucherDTO createUserVoucher(@RequestBody UserVoucherDTO userVoucherDTO) {
        return userVoucherUsecase.createUserVoucher(userVoucherDTO);
    }

    @Operation(summary = "Update user voucher", description = "Updates an existing user voucher", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the user voucher"),
            @ApiResponse(responseCode = "404", description = "User voucher not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserVoucherDTO> updateUserVoucher(@PathVariable String id, @RequestBody UserVoucherDTO userVoucherDTO) {
        try {
            UserVoucherDTO updatedUserVoucher = userVoucherUsecase.updateUserVoucher(id, userVoucherDTO);
            return ResponseEntity.ok(updatedUserVoucher);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error updating user voucher: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete user voucher", description = "Deletes a user voucher by its ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the user voucher"),
            @ApiResponse(responseCode = "404", description = "User voucher not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserVoucher(@PathVariable String id) {
        try {
            userVoucherUsecase.deleteUserVoucher(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting user voucher: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 