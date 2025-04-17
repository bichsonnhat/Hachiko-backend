package com.mongodb.starter.repositories.mongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.entity.VoucherEntity;
import com.mongodb.starter.repositories.interfaces.VoucherRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBVoucherRepository implements VoucherRepository {
    private final MongoClient client;
    private MongoCollection<VoucherEntity> voucherCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "vouchers";

    public MongoDBVoucherRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        voucherCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, VoucherEntity.class);
    }

    @Override
    public List<VoucherEntity> findAll() {
        return voucherCollection.find().into(new java.util.ArrayList<>());
    }

    @Override
    public List<VoucherEntity> findByUserId(String userId) {
        ObjectId userObjectId = new ObjectId(userId);

        return voucherCollection.find(new Document("$or", Arrays.asList(
                new Document("type", "PUBLIC"),
                new Document("userId", userObjectId)))).into(new ArrayList<>());
    }

}
