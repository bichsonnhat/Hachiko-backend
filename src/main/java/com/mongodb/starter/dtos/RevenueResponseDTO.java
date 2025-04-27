package com.mongodb.starter.dtos;

import java.util.List;

public record RevenueResponseDTO(
        List<String> labels,
        List<Double> data) {

}
