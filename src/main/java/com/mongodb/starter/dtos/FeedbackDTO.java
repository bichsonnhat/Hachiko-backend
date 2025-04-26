package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.FeedbackEntity;

public record FeedbackDTO(
        String id,
        String userId,
        String feedbackContent) {

    public FeedbackDTO(FeedbackEntity feedbackEntity) {
        this(
                feedbackEntity.getId() == null ? null : feedbackEntity.getId().toHexString(),
                feedbackEntity.getUserId() == null ? null : feedbackEntity.getUserId().toHexString(),
                feedbackEntity.getFeedbackContent());
    }

    public FeedbackEntity toFeedbackEntity() {
        return new FeedbackEntity(
                id == null ? null : new ObjectId(id),
                userId == null ? null : new ObjectId(userId),
                feedbackContent);
    }
}
