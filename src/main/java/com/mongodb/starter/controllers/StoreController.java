package com.mongodb.starter.controllers;

import com.mongodb.starter.dtos.StoreDTO;
import com.mongodb.starter.usecases.interfaces.StoreUsecase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import java.util.List;

@RestController
@RequestMapping("/stores")
@Tag(name = "stores")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class StoreController {
        private final static Logger LOGGER = LoggerFactory.getLogger(StoreController.class);
        private final StoreUsecase storeUsecase;

        public StoreController(StoreUsecase storeUsecase) {
                this.storeUsecase = storeUsecase;
        }

        @Operation(summary = "Get all stores", description = "Retrieves a list of all stores in the system", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all stores")
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<StoreDTO> getStores() {
                return storeUsecase.getStores();
        }

        @Operation(summary = "Get store by ID", description = "Retrieves a specific store using its ID", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the store"),
                        @ApiResponse(responseCode = "404", description = "Store not found")
        })
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public StoreDTO getStore(
                        @Parameter(description = "ID of the store to retrieve") @PathVariable String id) {
                return storeUsecase.getStore(id);
        }

        @Operation(summary = "Create a new store", description = "Creates a new store with the provided information", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Store successfully created"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public StoreDTO createStore(
                        @Parameter(description = "Store information for creation") @RequestBody StoreDTO storeDTO) {
                return storeUsecase.createStore(storeDTO.toStoreEntity());
        }

        @Operation(summary = "Update an existing store", description = "Updates an existing store with new information", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Store successfully updated"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "404", description = "Store not found")
        })
        @PutMapping
        @ResponseStatus(HttpStatus.OK)
        public StoreDTO updateStore(
                        @Parameter(description = "Updated store information") @RequestBody StoreDTO storeDTO) {
                return storeUsecase.updateStore(storeDTO.toStoreEntity());
        }

        @Operation(summary = "Delete a store", description = "Deletes a store by its ID", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Store successfully deleted"),
                        @ApiResponse(responseCode = "404", description = "Store not found")
        })
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteStore(
                        @Parameter(description = "ID of the store to delete") @PathVariable String id) {
                storeUsecase.deleteStore(id);
        }

        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public final Exception handleAllExceptions(RuntimeException e) {
                LOGGER.error("Internal server error.", e);
                return e;
        }
}