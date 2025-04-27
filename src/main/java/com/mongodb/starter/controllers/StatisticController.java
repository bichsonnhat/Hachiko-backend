package com.mongodb.starter.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.DashboardResponseDTO;
import com.mongodb.starter.dtos.OrderDTO;
import com.mongodb.starter.dtos.ProductStatisticResponseDTO;
import com.mongodb.starter.dtos.RevenueResponseDTO;
import com.mongodb.starter.usecases.interfaces.StatisticUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/statistic")
@Tag(name = "statistic")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class StatisticController {
    private final StatisticUsecase statisticUsecase;

    public StatisticController(StatisticUsecase statisticUsecase) {
        this.statisticUsecase = statisticUsecase;
    }

    @Operation(summary = "Get dashboard statistics", description = "Retrieves dashboard statistics for the system", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved dashboard statistics")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public DashboardResponseDTO getDashboardStatistics() {
        return statisticUsecase.getDashboardStatistics();
    }

    @Operation(summary = "Get order statistics", description = "Retrieves order statistics based on order status", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved order statistics")
    @GetMapping("/order")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getOrderStatistic(
            @RequestParam String orderStatus,
            @RequestParam long startDate,
            @RequestParam long endDate) {
        return statisticUsecase.getOrderStatistic(orderStatus, startDate, endDate);
    }

    @Operation(summary = "Get popular products", description = "Retrieves a list of popular products based on sales statistics", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved popular products")
    @GetMapping("/popular-products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductStatisticResponseDTO> getPopularProducts() {
        return statisticUsecase.getPopularProducts();
    }

    @Operation(summary = "Get revenue statistics", description = "Retrieves revenue statistics for a given date range", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved revenue statistics")
    @GetMapping("/revenue")
    @ResponseStatus(HttpStatus.OK)
    public RevenueResponseDTO getRevenue(
            @RequestParam long startDate,
            @RequestParam long endDate) {
        return statisticUsecase.getRevenue(startDate, endDate);
    }

}
