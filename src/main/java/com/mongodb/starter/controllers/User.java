package com.mongodb.starter.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.usecases.interfaces.UserUsecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/users")
@Tag(name = "users")
@SecurityScheme(
    name = "api_key",
    type = SecuritySchemeType.APIKEY,
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY",
    description = "API key for authentication. Add 'X-API-KEY' header with your API key."
)
public class User {

    private final static Logger LOGGER = LoggerFactory.getLogger(User.class);
    private final UserUsecase userUsecase;

    public User(UserUsecase userUsecase) {
        this.userUsecase = userUsecase;
    }

    @Operation(
        summary = "Create a new user",
        description = "Creates a new user account with the provided information. Requires API key authentication.",
        security = { @SecurityRequirement(name = "api_key") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", 
                    description = "User successfully created"),
        @ApiResponse(responseCode = "400", 
                    description = "Invalid input data"),
        @ApiResponse(responseCode = "401", 
                    description = "Unauthorized - Invalid or missing API key"),
        @ApiResponse(responseCode = "409", 
                    description = "User already exists")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserEntity createUser(
            @Parameter(description = "User information for account creation",
                      required = true)
            @RequestBody UserDTO userDTO) {
        return userUsecase.createUser(userDTO.toUserEntity());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}