package com.mongodb.starter.repositories.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.starter.entity.ProductEntity;
import com.mongodb.starter.repositories.interfaces.ProductRepository;

import static com.mongodb.client.model.ReturnDocument.AFTER;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBProductRepository implements ProductRepository {
    
    private final MongoClient client;
    private MongoCollection<ProductEntity> productCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "products";

    public MongoDBProductRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        productCollection = client.getDatabase(DATABASE_NAME)
                                .getCollection(COLLECTION_NAME, ProductEntity.class);
    }

    @Override
    public ProductEntity insertOne(ProductEntity productEntity) {
        productCollection.insertOne(productEntity);
        return productEntity;
    }

    @Override
    public List<ProductEntity> findAll() {
        return productCollection.find().into(new ArrayList<>());
    }

    @Override
    public ProductEntity findOne(String id) {
        return productCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public ProductEntity updateOne(ProductEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return productCollection.findOneAndReplace(
            eq("_id", entity.getId()), 
            entity, 
            options
        );
    }

    @Override
    public void deleteOne(String id) {
        productCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    @Override
    public List<ProductEntity> findByCategory(String id) {
        return productCollection.find(eq("categoryID", new ObjectId(id))).into(new ArrayList<>());
    }
}