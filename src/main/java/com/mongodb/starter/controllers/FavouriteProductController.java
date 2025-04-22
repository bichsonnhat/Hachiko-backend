package com.mongodb.starter.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.FavouriteProductDTO;
import com.mongodb.starter.dtos.FavouriteProductsResponse;
import com.mongodb.starter.usecases.interfaces.FavouriteProductUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/favourite-products")
@Tag(name = "favourite-products")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class FavouriteProductController {
        private final static Logger LOGGER = LoggerFactory.getLogger(FavouriteProductController.class);
        private final FavouriteProductUsecase favouriteProductUsecase;

        public FavouriteProductController(FavouriteProductUsecase favouriteProductUsecase) {
                this.favouriteProductUsecase = favouriteProductUsecase;
        }

        @Operation(summary = "Get all favourite products by userId", description = "Retrieves a list of all favourite products for the authenticated user", security = {
                        @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved all favourite products"),
                        @ApiResponse(responseCode = "404", description = "User not found"),
        })
        @GetMapping("/{userId}")
        @ResponseStatus(HttpStatus.OK)
        public FavouriteProductsResponse getFavouriteProducts(@PathVariable String userId) {
                return favouriteProductUsecase.getFavouriteProductsByUserId(userId);
        }

        @Operation(summary = "Update favourite product list", description = "Add new product into user's favourite product list", security = {
                        @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Successfully updated the favourite product list"),
                        @ApiResponse(responseCode = "404", description = "Product not found"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping("")
        @ResponseStatus(HttpStatus.CREATED)
        public FavouriteProductDTO addNewProductIntoFavouriteProductList(
                        @RequestBody FavouriteProductDTO favouriteProductDTO) {
                return favouriteProductUsecase
                                .addNewProductIntoFavouriteProductList(favouriteProductDTO.toFavouriteProductEntity());
        }

        @Operation(summary = "Check if product is in favourite product list", description = "Check if a product is in the user's favourite product list", security = {
                        @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Product is in the favourite product list"),
                        @ApiResponse(responseCode = "404", description = "Product not found") })
        @GetMapping("/{userId}/{productId}")
        @ResponseStatus(HttpStatus.OK)
        public boolean checkIfProductIsInFavouriteProductList(
                        @PathVariable String userId, @PathVariable String productId) {
                return favouriteProductUsecase.checkIfProductIsInFavouriteProductList(userId, productId);
        }

        @DeleteMapping("{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void removeProductFromFavouriteProductList(@PathVariable String id) {
                favouriteProductUsecase.removeProductFromFavouriteProductList(id);
        }

        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public final Exception handleAllExceptions(RuntimeException e) {
                LOGGER.error("Internal server error.", e);
                return e;
        }
}
