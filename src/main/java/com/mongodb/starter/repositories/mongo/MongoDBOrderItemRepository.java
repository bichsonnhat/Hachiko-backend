package com.mongodb.starter.repositories.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.starter.entity.OrderItemEntity;
import com.mongodb.starter.repositories.interfaces.OrderItemRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBOrderItemRepository implements OrderItemRepository {
    private final MongoClient client;
    private MongoCollection<OrderItemEntity> orderItemCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "orderItems";

    public MongoDBOrderItemRepository(MongoClient client) {
        this.client = client;
    }

    @PostConstruct
    void init() {
        orderItemCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, OrderItemEntity.class);
    }

    @Override
    public List<OrderItemEntity> bulkInsert(List<OrderItemEntity> orderItems) {
        orderItemCollection.insertMany(orderItems);
        return orderItems;
    }

    @Override
    public void deleteAllByOrderId(String orderId) {
        orderItemCollection.deleteMany(eq("orderId", new ObjectId(orderId)));
    }

    @Override
    public List<OrderItemEntity> findAllByOrderId(String orderId) {
        return orderItemCollection.find(eq("orderId", new ObjectId(orderId))).into(new java.util.ArrayList<>());
    }

}
