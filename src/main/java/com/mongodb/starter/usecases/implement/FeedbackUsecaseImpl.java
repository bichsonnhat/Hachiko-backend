package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.FeedbackDTO;
import com.mongodb.starter.dtos.FeedbackOrderDTO;
import com.mongodb.starter.entity.FeedbackEntity;
import com.mongodb.starter.entity.FeedbackOrderEntity;
import com.mongodb.starter.repositories.interfaces.FeedbackOrderRepository;
import com.mongodb.starter.repositories.interfaces.FeedbackRepository;
import com.mongodb.starter.usecases.interfaces.FeedbackUsecase;

@Service
public class FeedbackUsecaseImpl implements FeedbackUsecase {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackOrderRepository feedbackOrderRepository;

    public FeedbackUsecaseImpl(FeedbackRepository feedbackRepository, FeedbackOrderRepository feedbackOrderRepository) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackOrderRepository = feedbackOrderRepository;
    }

    @Override
    public FeedbackDTO addNewFeedback(FeedbackEntity feedbackEntity) {
        FeedbackEntity savedEntity = feedbackRepository.addNewFeedback(feedbackEntity);
        return new FeedbackDTO(savedEntity);
    }

    @Override
    public FeedbackOrderDTO addNewFeedbackOrder(FeedbackOrderEntity feedbackOrderEntity) {
        FeedbackOrderEntity savedEntity = feedbackOrderRepository.addNewFeedbackOrder(feedbackOrderEntity);
        return new FeedbackOrderDTO(savedEntity);
    }

    @Override
    public List<FeedbackOrderDTO> getAllFeedbackOrders() {
        List<FeedbackOrderEntity> feedbackOrderEntities = feedbackOrderRepository.getAllFeedbackOrders();
        return feedbackOrderEntities.stream()
                .map(FeedbackOrderDTO::new)
                .toList();
    }

}
