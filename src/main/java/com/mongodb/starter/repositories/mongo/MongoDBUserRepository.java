package com.mongodb.starter.repositories.mongo;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.repositories.interfaces.UserRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBUserRepository implements UserRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
                                                                           .readPreference(ReadPreference.primary())
                                                                           .readConcern(ReadConcern.MAJORITY)
                                                                           .writeConcern(WriteConcern.MAJORITY)
                                                                           .build();
    private final MongoClient client;
    private MongoCollection<UserEntity> userCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "users";

    public MongoDBUserRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, UserEntity.class);
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        userEntity.setId(new ObjectId());
        // userCollection.insertOne(userEntity);
        return userEntity;
    }
}
