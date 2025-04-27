package com.mongodb.starter.repositories.mongo;

import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.entity.FeedbackEntity;
import com.mongodb.starter.repositories.interfaces.FeedbackRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBFeedbackRepository implements FeedbackRepository {
    private final MongoClient client;
    private MongoCollection<FeedbackEntity> feedbackCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "feedbacks";

    public MongoDBFeedbackRepository(MongoClient client) {
        this.client = client;
    }

    @PostConstruct
    void init() {
        feedbackCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, FeedbackEntity.class);
    }

    @Override
    public FeedbackEntity addNewFeedback(FeedbackEntity feedbackEntity) {
        feedbackCollection.insertOne(feedbackEntity);
        return feedbackEntity;
    }

}
