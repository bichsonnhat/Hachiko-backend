package com.mongodb.starter.controllers;

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
import com.mongodb.starter.dtos.ReviewDTO;
import com.mongodb.starter.dtos.ReviewRequestDTO;
import com.mongodb.starter.dtos.ProductReviewsResponseDTO;
import com.mongodb.starter.dtos.DeleteReviewResponseDTO;
import com.mongodb.starter.usecases.interfaces.ReviewUsecase;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@RestController 
@RequestMapping("/reviews")
@Tag(name = "reviews")
@SecurityScheme(
    name = "api_key",
    type = SecuritySchemeType.APIKEY,
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY",
    description = "API key for authentication. Add 'X-API-KEY' header with your API key."
)
public class ReviewController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewUsecase reviewUsecase;

    public ReviewController(ReviewUsecase reviewUsecase) {
        this.reviewUsecase = reviewUsecase;
    }

    @Operation(summary = "Get product reviews",
           description = "Get all reviews for a specific product, including the current user's review if exists",
           security = { @SecurityRequirement(name = "api_key") }
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved product reviews"),
        @ApiResponse(responseCode = "400", description = "Invalid product ID"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductReviewsResponseDTO getProductReviews(
            @Parameter(description = "ID of the product to get reviews for")
            @PathVariable String productId,
            @Parameter(description = "ID of the current user to check if they have reviewed this product")
            @RequestParam(value = "userId", required = false) String userId) {
        return reviewUsecase.getProductReviews(productId, userId);
    }

    @Operation(summary = "Create a new review",
               description = "Create a new review for a product",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Review successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or rating out of range"),
        @ApiResponse(responseCode = "409", description = "User has already reviewed this product")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDTO createReview(
            @Parameter(description = "Review information for creation")
            @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewUsecase.createReview(reviewRequestDTO);
    }

    @Operation(summary = "Update an existing review",
               description = "Update an existing review with new information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or rating out of range"),
        @ApiResponse(responseCode = "403", description = "You can only update your own reviews"),
        @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @PutMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ReviewDTO updateReview(
            @Parameter(description = "ID of the review to update")
            @PathVariable String reviewId,
            @Parameter(description = "Updated review information")
            @RequestBody ReviewRequestDTO reviewRequestDTO) {
        return reviewUsecase.updateReview(reviewId, reviewRequestDTO);
    }

    @Operation(summary = "Delete a review",
               description = "Delete a review by its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review successfully deleted"),
        @ApiResponse(responseCode = "400", description = "Invalid review ID"),
        @ApiResponse(responseCode = "404", description = "Review not found")
    })
    @DeleteMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteReviewResponseDTO deleteReview(
            @Parameter(description = "ID of the review to delete")
            @PathVariable String reviewId) {
        return reviewUsecase.deleteReview(reviewId);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final Exception handleBadRequestExceptions(IllegalArgumentException e) {
        LOGGER.error("Bad request error.", e);
        return e;
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public final Exception handleConflictExceptions(IllegalStateException e) {
        LOGGER.error("Conflict error.", e);
        return e;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}