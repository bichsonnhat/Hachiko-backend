package com.mongodb.starter.repositories.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.repositories.interfaces.UserRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBUserRepository implements UserRepository {
    private final MongoClient client;
    private MongoCollection<UserEntity> userCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "users";

    public MongoDBUserRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, UserEntity.class);
    }

    @Override
    public List<UserEntity> findAll() {
        return userCollection.find().into(new java.util.ArrayList<>());
    }

    @Override
    public Optional<UserEntity> findById(String id) {
        UserEntity userEntity = userCollection.find(Filters.eq("_id", id)).first();
        return Optional.ofNullable(userEntity);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        UserEntity userEntity = userCollection.find(Filters.eq("email", email)).first();
        return Optional.ofNullable(userEntity);
    }

    @Override
    public Optional<UserEntity> findByPhoneNumber(String phoneNumber) {
        UserEntity userEntity = userCollection.find(Filters.eq("phoneNumber", phoneNumber)).first();
        return Optional.ofNullable(userEntity);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (userEntity.getId() == null || userEntity.getId().isEmpty()) {
            throw new IllegalArgumentException("User ID must be provided");
        }
        userCollection.insertOne(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        if (userEntity.getId() == null || userEntity.getId().isEmpty()) {
            throw new IllegalArgumentException("User ID must be provided for update");
        }
        
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        
        return userCollection.findOneAndReplace(
                Filters.eq("_id", userEntity.getId()),
                userEntity,
                options);
    }

    @Override
    public void deleteById(String id) {
        userCollection.deleteOne(Filters.eq("_id", id));
    }
}
