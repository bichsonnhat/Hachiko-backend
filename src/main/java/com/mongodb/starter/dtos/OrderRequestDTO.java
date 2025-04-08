package com.mongodb.starter.dtos;

import java.util.List;

public record OrderRequestDTO(
                OrderDTO order,
                List<OrderItemDTO> orderItems) {
}