package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.FeedbackOrderEntity;

@Repository
public interface FeedbackOrderRepository {
    FeedbackOrderEntity addNewFeedbackOrder(FeedbackOrderEntity feedbackOrderEntity);

    List<FeedbackOrderEntity> getAllFeedbackOrders();
}
