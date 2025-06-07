package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.FeedbackEntity;

public record FeedbackDTO(
        String id,
        String userId,
        String username,
        String feedbackContent) {

    public FeedbackDTO(FeedbackEntity feedbackEntity) {
        this(
                feedbackEntity.getId() == null ? null : feedbackEntity.getId().toHexString(),
                feedbackEntity.getUserId(),
                feedbackEntity.getUsername(),
                feedbackEntity.getFeedbackContent());
    }

    public FeedbackEntity toFeedbackEntity() {
        return new FeedbackEntity(
                id == null ? null : new ObjectId(id),
                userId,
                username,
                feedbackContent);
    }
}
