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
import com.mongodb.starter.entity.NotificationEntity;
import com.mongodb.starter.repositories.interfaces.NotificationRepository;

import static com.mongodb.client.model.ReturnDocument.AFTER;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBNotificationRepository implements NotificationRepository {
    // private static final TransactionOptions txnOptions = TransactionOptions.builder()
    //         .readPreference(ReadPreference.primary())
    //         .readConcern(ReadConcern.MAJORITY)
    //         .writeConcern(WriteConcern.MAJORITY)
    //         .build();

    private final MongoClient client;
    private MongoCollection<NotificationEntity> notificationCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "notifications";

    public MongoDBNotificationRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        notificationCollection = client.getDatabase(DATABASE_NAME)
                                    .getCollection(COLLECTION_NAME, NotificationEntity.class);
    }

    @Override
    public NotificationEntity insertOne(NotificationEntity notificationEntity) {
        notificationCollection.insertOne(notificationEntity);
        return notificationEntity;
    }

    @Override
    public List<NotificationEntity> findAll() {
        return notificationCollection.find().into(new ArrayList<>());
    }

    @Override
    public NotificationEntity findOne(String id) {
        return notificationCollection.find(eq("_id", new ObjectId(id))).first();
    }

    @Override
    public NotificationEntity updateOne(NotificationEntity entity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return notificationCollection.findOneAndReplace(
            eq("_id", entity.getId()), 
            entity, 
            options
        );
    }

    @Override
    public void deleteOne(String id) {
        notificationCollection.deleteOne(eq("_id", new ObjectId(id)));
    }
}