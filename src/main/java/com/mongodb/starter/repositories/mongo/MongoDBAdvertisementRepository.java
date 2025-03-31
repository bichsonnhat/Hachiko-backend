package com.mongodb.starter.repositories.mongo;

import org.springframework.stereotype.Repository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.entity.AdvertisementEntity;
import com.mongodb.starter.repositories.interfaces.AdvertisementRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBAdvertisementRepository implements AdvertisementRepository {
    private final MongoClient client;
    private MongoCollection<AdvertisementEntity> advertisementCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "advertisements";

    public MongoDBAdvertisementRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        advertisementCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, AdvertisementEntity.class);
    }

    @Override
    public AdvertisementEntity insertOne(AdvertisementEntity advertisementEntity){
        advertisementCollection.insertOne(advertisementEntity);
        return advertisementEntity;
    }
}