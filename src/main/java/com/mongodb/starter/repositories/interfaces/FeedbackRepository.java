package com.mongodb.starter.repositories.interfaces;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.FeedbackEntity;

@Repository
public interface FeedbackRepository {
    FeedbackEntity addNewFeedback(FeedbackEntity feedbackEntity);
}
