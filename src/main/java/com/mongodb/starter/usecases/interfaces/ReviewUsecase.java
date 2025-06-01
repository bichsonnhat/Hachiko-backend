package com.mongodb.starter.usecases.interfaces;

import com.mongodb.starter.dtos.ReviewDTO;
import com.mongodb.starter.dtos.ReviewRequestDTO;
import com.mongodb.starter.dtos.ProductReviewsResponseDTO;
import com.mongodb.starter.dtos.DeleteReviewResponseDTO;

public interface ReviewUsecase {
    
    ProductReviewsResponseDTO getProductReviews(String productId, String userId);
    
    ReviewDTO createReview(ReviewRequestDTO reviewRequestDTO);
    
    ReviewDTO updateReview(String reviewId, ReviewRequestDTO reviewRequestDTO);
    
    DeleteReviewResponseDTO deleteReview(String reviewId);
}