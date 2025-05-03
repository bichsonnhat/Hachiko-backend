package com.mongodb.starter.repositories.mongo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.starter.entity.UserNotification;
import com.mongodb.starter.repositories.interfaces.UserNotificationRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBUserNotificationRepository implements UserNotificationRepository {
    private final MongoClient client;
    private MongoCollection<UserNotification> userNotificationCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "userNotifications";

    public MongoDBUserNotificationRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userNotificationCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, UserNotification.class);
    }

    @Override
    public List<UserNotification> findAll() {
        return userNotificationCollection.find().into(new java.util.ArrayList<>());
    }

    @Override
    public Optional<UserNotification> findById(String id) {
        UserNotification userNotification = userNotificationCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        return Optional.ofNullable(userNotification);
    }

    @Override
    public List<UserNotification> findByUserId(String userId) {
        return userNotificationCollection.find(Filters.eq("userId", userId))
                .into(new java.util.ArrayList<>());
    }

    @Override
    public List<UserNotification> findByNotificationId(String notificationId) {
        return userNotificationCollection.find(Filters.eq("notificationId", new ObjectId(notificationId)))
                .into(new java.util.ArrayList<>());
    }

    @Override
    public UserNotification save(UserNotification userNotification) {
        if (userNotification.getId() == null) {
            userNotification.setId(new ObjectId());
        }
        
        Date now = new Date();
        if (userNotification.getCreatedAt() == null) {
            userNotification.setCreatedAt(now);
        }
        userNotification.setUpdatedAt(now);
        
        userNotificationCollection.insertOne(userNotification);
        return userNotification;
    }

    @Override
    public UserNotification update(UserNotification userNotification) {
        userNotification.setUpdatedAt(new Date());
        
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        
        return userNotificationCollection.findOneAndReplace(
                Filters.eq("_id", userNotification.getId()),
                userNotification,
                options);
    }

    @Override
    public void deleteById(String id) {
        userNotificationCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
} 