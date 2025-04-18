package com.mongodb.starter.dtos;

import java.util.List;

public record OrderResponseDTO(
        OrderDTO order,
        List<OrderItemDTO> orderItems) {
    public OrderResponseDTO(OrderDTO order, List<OrderItemDTO> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
}
