package com.mongodb.starter.repositories.mongo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.entity.FeedbackOrderEntity;
import com.mongodb.starter.repositories.interfaces.FeedbackOrderRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBFeedbackOrderRepository implements FeedbackOrderRepository {
    private final MongoClient client;
    private MongoCollection<FeedbackOrderEntity> feedbackOrderCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "feedbackOrders";

    public MongoDBFeedbackOrderRepository(MongoClient client) {
        this.client = client;
    }

    @PostConstruct
    void init() {
        feedbackOrderCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME,
                FeedbackOrderEntity.class);
    }

    @Override
    public FeedbackOrderEntity addNewFeedbackOrder(FeedbackOrderEntity feedbackOrderEntity) {
        feedbackOrderCollection.insertOne(feedbackOrderEntity);
        return feedbackOrderEntity;
    }

    @Override
    public List<FeedbackOrderEntity> getAllFeedbackOrders() {
        return feedbackOrderCollection.find().into(new java.util.ArrayList<>());
    }

}
