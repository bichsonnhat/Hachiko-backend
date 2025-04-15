package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.mongodb.starter.dtos.NotificationDTO;
import com.mongodb.starter.usecases.interfaces.NotificationUsecase;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
@RestController
@RequestMapping("/notifications")
@Tag(name = "notifications")
@SecurityScheme(
    name = "api_key",
    type = SecuritySchemeType.APIKEY,
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY",
    description = "API key for authentication. Add 'X-API-KEY' header with your API key."
)
public class NotificationController {
    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationUsecase notificationUsecase;

    public NotificationController(NotificationUsecase notificationUsecase) {
        this.notificationUsecase = notificationUsecase;
    }

    @Operation(summary = "Get all notifications",
               description = "Retrieves a list of all notifications in the system",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all notifications")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationDTO> getAllNotifications() {
        return notificationUsecase.getAllNotifications();
    }

    @Operation(summary = "Get notification by ID",
               description = "Retrieves a specific notification using its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the notification"),
        @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationDTO getNotification(@PathVariable String id) {
        return notificationUsecase.getNotification(id);
    }

    @Operation(summary = "Create a new notification",
               description = "Creates a new notification with the provided information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Notification successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDTO createNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationUsecase.createNotification(notificationDTO);
    }

    @Operation(summary = "Update an existing notification",
               description = "Updates an existing notification with new information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notification successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NotificationDTO updateNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationUsecase.updateNotification(notificationDTO);
    }

    @Operation(summary = "Delete a notification",
               description = "Deletes a notification by its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Notification successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Notification not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable String id) {
        notificationUsecase.deleteNotification(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}