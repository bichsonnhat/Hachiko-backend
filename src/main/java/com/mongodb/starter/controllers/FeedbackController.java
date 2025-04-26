package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.FeedbackDTO;
import com.mongodb.starter.dtos.FeedbackOrderDTO;
import com.mongodb.starter.usecases.interfaces.FeedbackUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/feedback")
@Tag(name = "feedback")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class FeedbackController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);
    private final FeedbackUsecase feedbackUsecase;

    public FeedbackController(FeedbackUsecase feedbackUsecase) {
        this.feedbackUsecase = feedbackUsecase;
    }

    @Operation(summary = "Add new regular feedback", description = "Add new regular feedback for a product", security = {
            @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added the feedback"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/regular")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackDTO addNewRegularFeedback(
            @RequestBody FeedbackDTO feedbackDTO) {
        return feedbackUsecase.addNewFeedback(feedbackDTO.toFeedbackEntity());
    }

    @Operation(summary = "Add new order feedback", description = "Add new feedback for an order", security = {
            @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added the order feedback"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedbackOrderDTO addNewOrderFeedback(
            @RequestBody FeedbackOrderDTO feedbackOrderDTO) {
        return feedbackUsecase.addNewFeedbackOrder(feedbackOrderDTO.toFeedbackOrderEntity());
    }

    @Operation(summary = "Get all order feedbacks", description = "Retrieve all feedbacks for orders", security = {
            @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all order feedbacks"),
    })
    @GetMapping("")
    public List<FeedbackOrderDTO> getAllOrderFeedbacks() {
        return feedbackUsecase.getAllFeedbackOrders();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
