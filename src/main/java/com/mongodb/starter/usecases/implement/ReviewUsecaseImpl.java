package com.mongodb.starter.usecases.implement;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.ReviewDTO;
import com.mongodb.starter.dtos.ReviewRequestDTO;
import com.mongodb.starter.dtos.ProductReviewsResponseDTO;
import com.mongodb.starter.dtos.DeleteReviewResponseDTO;
import com.mongodb.starter.dtos.ReviewWithUserInfoDTO;
import com.mongodb.starter.entity.ReviewEntity;
import com.mongodb.starter.repositories.interfaces.ReviewRepository;
import com.mongodb.starter.usecases.interfaces.ReviewUsecase;

@Service
public class ReviewUsecaseImpl implements ReviewUsecase {

    private final ReviewRepository reviewRepository;

    public ReviewUsecaseImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ProductReviewsResponseDTO getProductReviews(String productId, String userId) {
        // Validate productId
        if (!ObjectId.isValid(productId)) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        // Get reviews with user info
        List<ReviewWithUserInfoDTO> reviews = reviewRepository.findByProductIdWithUserInfo(productId);
        
        // Calculate average rating
        double averageRating = reviewRepository.calculateAverageRating(productId);
        
        // Get total reviews count
        int totalReviews = reviewRepository.countByProductId(productId);
        
        // Get user's review if userId is provided
        ReviewDTO userReview = null;
        if (userId != null && !userId.isEmpty()) {
            ReviewEntity userReviewEntity = reviewRepository.findByUserIdAndProductId(userId, productId);
            if (userReviewEntity != null) {
                userReview = new ReviewDTO(userReviewEntity);
            }
        }

        return new ProductReviewsResponseDTO(reviews, averageRating, totalReviews, userReview);
    }

    @Override
    public ReviewDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        // Validate input
        validateReviewRequest(reviewRequestDTO);
        
        // Check if user already reviewed this product
        ReviewEntity existingReview = reviewRepository.findByUserIdAndProductId(
                reviewRequestDTO.userId(), 
                reviewRequestDTO.productId()
        );
        
        if (existingReview != null) {
            throw new IllegalStateException("You have already reviewed this product");
        }

        // Create new review entity
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setUserId(reviewRequestDTO.userId());
        reviewEntity.setProductId(new ObjectId(reviewRequestDTO.productId()));
        reviewEntity.setRating(reviewRequestDTO.rating());
        reviewEntity.setComment(reviewRequestDTO.comment());
        reviewEntity.setCreatedAt(new Date());
        reviewEntity.setUpdatedAt(new Date());

        // Save review
        ReviewEntity savedReview = reviewRepository.insertOne(reviewEntity);
        
        return new ReviewDTO(savedReview);
    }

    @Override
    public ReviewDTO updateReview(String reviewId, ReviewRequestDTO reviewRequestDTO) {
        // Validate input
        if (!ObjectId.isValid(reviewId)) {
            throw new IllegalArgumentException("Invalid review ID");
        }
        validateReviewRequest(reviewRequestDTO);

        // Find existing review
        ReviewEntity existingReview = reviewRepository.findOne(reviewId);
        if (existingReview == null) {
            throw new IllegalArgumentException("Review not found");
        }

        // Check if user owns this review
        if (!existingReview.getUserId().equals(reviewRequestDTO.userId())) {
            throw new IllegalStateException("You can only update your own reviews");
        }

        // Update review
        existingReview.setRating(reviewRequestDTO.rating());
        existingReview.setComment(reviewRequestDTO.comment());
        existingReview.setUpdatedAt(new Date());

        ReviewEntity updatedReview = reviewRepository.updateOne(existingReview);
        
        return new ReviewDTO(updatedReview);
    }

    @Override
    public DeleteReviewResponseDTO deleteReview(String reviewId) {
        // Validate reviewId
        if (!ObjectId.isValid(reviewId)) {
            throw new IllegalArgumentException("Invalid review ID");
        }

        // Check if review exists
        ReviewEntity existingReview = reviewRepository.findOne(reviewId);
        if (existingReview == null) {
            throw new IllegalArgumentException("Review not found");
        }

        // Delete review
        reviewRepository.deleteOne(reviewId);
        
        return new DeleteReviewResponseDTO("Review deleted successfully");
    }

    private void validateReviewRequest(ReviewRequestDTO reviewRequestDTO) {
        if (reviewRequestDTO.userId() == null || reviewRequestDTO.userId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID is required");
        }
        
        if (reviewRequestDTO.productId() == null || !ObjectId.isValid(reviewRequestDTO.productId())) {
            throw new IllegalArgumentException("Valid product ID is required");
        }
        
        if (reviewRequestDTO.rating() < 1 || reviewRequestDTO.rating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        
        if (reviewRequestDTO.comment() == null || reviewRequestDTO.comment().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment is required");
        }
        
        if (reviewRequestDTO.comment().length() > 1000) {
            throw new IllegalArgumentException("Comment must not exceed 1000 characters");
        }
    }
}