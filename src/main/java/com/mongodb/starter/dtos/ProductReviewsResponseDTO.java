package com.mongodb.starter.dtos;

import java.util.List;

public record ProductReviewsResponseDTO(
    List<ReviewWithUserInfoDTO> reviews,
    double averageRating,
    int totalReviews,
    ReviewDTO userReview
) {
}