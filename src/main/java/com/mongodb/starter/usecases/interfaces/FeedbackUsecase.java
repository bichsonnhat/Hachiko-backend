package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.FeedbackDTO;
import com.mongodb.starter.dtos.FeedbackOrderDTO;
import com.mongodb.starter.entity.FeedbackEntity;
import com.mongodb.starter.entity.FeedbackOrderEntity;

@Service
public interface FeedbackUsecase {
    FeedbackDTO addNewFeedback(FeedbackEntity feedbackEntity);

    FeedbackOrderDTO addNewFeedbackOrder(FeedbackOrderEntity feedbackOrderEntity);

    List<FeedbackOrderDTO> getAllFeedbackOrders();
}
