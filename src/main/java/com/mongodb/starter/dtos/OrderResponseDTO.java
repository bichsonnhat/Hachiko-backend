package com.mongodb.starter.dtos;

import java.util.List;

import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.entity.OrderItemEntity;

public record OrderResponseDTO(
        OrderEntity order,
        List<OrderItemEntity> orderItems) {
    public OrderResponseDTO(OrderEntity order, List<OrderItemEntity> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
}
