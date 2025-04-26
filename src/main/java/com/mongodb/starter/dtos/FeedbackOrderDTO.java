package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.FeedbackOrderEntity;

public record FeedbackOrderDTO(
        String id,
        String userId,
        String username,
        String orderId,
        String feedbackContent,
        int rating) {

    public FeedbackOrderDTO(FeedbackOrderEntity feedbackOrderEntity) {
        this(feedbackOrderEntity.getId() == null ? null : feedbackOrderEntity.getId().toHexString(),
                feedbackOrderEntity.getUserId() == null ? null : feedbackOrderEntity.getUserId().toHexString(),
                feedbackOrderEntity.getUsername(),
                feedbackOrderEntity.getOrderId() == null ? null : feedbackOrderEntity.getOrderId().toHexString(),
                feedbackOrderEntity.getFeedbackContent(),
                feedbackOrderEntity.getRating());
    }

    public FeedbackOrderEntity toFeedbackOrderEntity() {
        return new FeedbackOrderEntity(
                id == null ? null : new ObjectId(id),
                userId == null ? null : new ObjectId(userId),
                username,
                orderId == null ? null : new ObjectId(orderId),
                feedbackContent,
                rating);
    }

}