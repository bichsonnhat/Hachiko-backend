package com.mongodb.starter.repositories.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.repositories.interfaces.OrderRepository;

import jakarta.annotation.PostConstruct;

import static com.mongodb.client.model.ReturnDocument.AFTER;
import org.bson.types.ObjectId;

@Repository
public class MongoDBOrderRepository implements OrderRepository {

    private final MongoClient client;
    private MongoCollection<OrderEntity> orderCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "orders";

    public MongoDBOrderRepository(MongoClient client) {
        this.client = client;
    }

    @PostConstruct
    void init() {
        orderCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, OrderEntity.class);
    }

    @Override
    public OrderEntity insertOne(OrderEntity orderEntity) {
        orderCollection.insertOne(orderEntity);
        return orderEntity;
    }

    @Override
    public void deleteOne(String id) {
        orderCollection.deleteOne(new org.bson.Document("_id", new ObjectId(id)));
    }

    @Override
    public OrderEntity updateOne(OrderEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return orderCollection.findOneAndReplace(eq("_id", entity.getId()), entity, options);
    }

    @Override
    public OrderEntity findById(String id) {
        return orderCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public List<OrderEntity> findAll() {
        return orderCollection.find().into(new java.util.ArrayList<>());
    }

    @Override
    public List<OrderEntity> findAllByUserId(String userId) {
        return orderCollection.find(eq("userId", new ObjectId(userId))).into(new java.util.ArrayList<>());
    }
}
