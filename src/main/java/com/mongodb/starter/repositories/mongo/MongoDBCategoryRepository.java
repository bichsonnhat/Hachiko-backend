package com.mongodb.starter.repositories.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.Sorts;
import com.mongodb.starter.entity.CategoryEntity;
import com.mongodb.starter.repositories.interfaces.CategoryRepository;

import static com.mongodb.client.model.ReturnDocument.AFTER;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBCategoryRepository implements CategoryRepository {
    private final MongoClient client;
    private MongoCollection<CategoryEntity> categoryCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "categories";

    public MongoDBCategoryRepository(MongoClient client) {
        this.client = client;
    }

    @PostConstruct
    void init() {
        categoryCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, CategoryEntity.class);
    }

    @Override
    public CategoryEntity insertOne(CategoryEntity categoryEntity) {
        categoryCollection.insertOne(categoryEntity);
        return categoryEntity;
    }

    @Override
    public void deleteOne(String id) {
        categoryCollection.deleteOne(eq("_id", new ObjectId(id)));
    }

    @Override
    public List<CategoryEntity> findAll() {
        return categoryCollection.find().sort(Sorts.descending("name")).into(new ArrayList<>());
    }

    @Override
    public CategoryEntity findOne(String id) {
        return categoryCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public CategoryEntity updateOne(CategoryEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return categoryCollection.findOneAndReplace(eq("_id", entity.getId()), entity, options);
    }

}
