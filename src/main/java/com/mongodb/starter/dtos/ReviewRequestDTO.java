package com.mongodb.starter.dtos;

public record ReviewRequestDTO(
    String userId,
    String productId,
    int rating,
    String comment
) {
}