package com.mongodb.starter.dtos;

public record DashboardResponseDTO(
        int numberOfCompletedOrders,
        int numberOfPendingOrders,
        int numberOfCancelledOrders,
        double totalRevenue) {

}
