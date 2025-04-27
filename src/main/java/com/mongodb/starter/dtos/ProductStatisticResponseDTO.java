package com.mongodb.starter.dtos;

public record ProductStatisticResponseDTO(
        String productId,
        String productName,
        String productImage,
        int quantitySold) {

}
