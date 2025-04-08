package com.mongodb.starter.repositories.mongo;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.starter.entity.StoreEntity;
import com.mongodb.starter.repositories.interfaces.StoreRepository;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBStoreRepository implements StoreRepository {
    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();
    private final MongoClient client;
    private MongoCollection<StoreEntity> storeCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "stores";

    public MongoDBStoreRepository(MongoClient client) {
        this.client = client;
    }
    @PostConstruct
    void init() {
        storeCollection = client.getDatabase(DATABASE_NAME).getCollection(COLLECTION_NAME, StoreEntity.class);
    }

    @Override
    public StoreEntity insertOne(StoreEntity storeEntity) {
        storeCollection.insertOne(storeEntity);
        return storeEntity;
    }

    @Override
    public void deleteOne(String id) {
        storeCollection.deleteOne(eq("_id", new ObjectId(id)));

    }

    @Override
    public List<StoreEntity> findAll() {
        return storeCollection.find().into(new ArrayList<>());
    }

    @Override
    public StoreEntity findOne(String id) {
        return storeCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public StoreEntity updateOne(StoreEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return storeCollection.findOneAndReplace(eq("_id", entity.getId()), entity, options);
    }
}
