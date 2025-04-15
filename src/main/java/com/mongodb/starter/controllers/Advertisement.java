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
import com.mongodb.starter.dtos.AdvertisementDTO;
import com.mongodb.starter.usecases.interfaces.AdvertisementUsecase;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
@RestController
@RequestMapping("/advertisements")
@Tag(name = "advertisements")
@SecurityScheme(
    name = "api_key",
    type = SecuritySchemeType.APIKEY,
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY",
    description = "API key for authentication. Add 'X-API-KEY' header with your API key."
)
public class Advertisement {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(Advertisement.class);
    private final AdvertisementUsecase advertisementUsecase;

    public Advertisement(AdvertisementUsecase advertisementUsecase) {
        this.advertisementUsecase = advertisementUsecase;
    }

    @Operation(summary = "Create a new advertisement", 
               description = "Creates a new advertisement with the provided details",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Advertisement created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdvertisementDTO createAdvertisement(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementUsecase.createAdvertisement(advertisementDTO);
    }

    @Operation(summary = "Get all advertisements",
               description = "Retrieves a list of all advertisements",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AdvertisementDTO> getCategories() {
        return advertisementUsecase.getAdvertisements();
    }

    @Operation(summary = "Get advertisement by ID",
               description = "Retrieves a specific advertisement by its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the advertisement"),
        @ApiResponse(responseCode = "404", description = "Advertisement not found")
    })
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDTO getCategory(@PathVariable String id) {
        return advertisementUsecase.getAdvertisement(id);
    }

    @Operation(summary = "Update an advertisement",
               description = "Updates an existing advertisement with new information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Advertisement updated successfully"),
        @ApiResponse(responseCode = "404", description = "Advertisement not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public AdvertisementDTO updateCategory(@RequestBody AdvertisementDTO advertisementDTO) {
        return advertisementUsecase.updateAdvertisement(advertisementDTO);
    }

    @Operation(summary = "Delete an advertisement",
               description = "Deletes an advertisement by its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Advertisement deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Advertisement not found")
    })
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable String id) {
        advertisementUsecase.deleteAdvertisement(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}