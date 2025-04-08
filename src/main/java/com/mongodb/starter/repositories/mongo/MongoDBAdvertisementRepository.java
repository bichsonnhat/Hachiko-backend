package com.mongodb.starter.repositories.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.eq;

// import com.mongodb.ReadConcern;
// import com.mongodb.ReadPreference;
// import com.mongodb.TransactionOptions;
// import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.starter.entity.AdvertisementEntity;
import com.mongodb.starter.repositories.interfaces.AdvertisementRepository;

import static com.mongodb.client.model.ReturnDocument.AFTER;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBAdvertisementRepository implements AdvertisementRepository {
    // private static final TransactionOptions txnOptions = TransactionOptions.builder()
    //         .readPreference(ReadPreference.primary())
    //         .readConcern(ReadConcern.MAJORITY)
    //         .writeConcern(WriteConcern.MAJORITY)
    //         .build();

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

    @Override
    public List<AdvertisementEntity> findAll() {
        return advertisementCollection.find().into(new ArrayList<>());
    }

    @Override
    public AdvertisementEntity findOne(String id) {
        return advertisementCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public AdvertisementEntity updateOne(AdvertisementEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return advertisementCollection.findOneAndReplace(eq("_id", entity.getId()), entity, options);
    }

    @Override
    public void deleteOne(String id) {
        advertisementCollection.deleteOne(eq("_id", new ObjectId(id)));
    }
}