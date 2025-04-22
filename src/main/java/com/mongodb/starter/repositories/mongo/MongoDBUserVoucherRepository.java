package com.mongodb.starter.repositories.mongo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.starter.entity.UserVoucher;
import com.mongodb.starter.repositories.interfaces.UserVoucherRepository;

import jakarta.annotation.PostConstruct;

@Repository
public class MongoDBUserVoucherRepository implements UserVoucherRepository {
    private final MongoClient client;
    private MongoCollection<UserVoucher> userVoucherCollection;
    private final String DATABASE_NAME = "Hachiko";
    private final String COLLECTION_NAME = "userVouchers";

    public MongoDBUserVoucherRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        userVoucherCollection = client.getDatabase(DATABASE_NAME)
                .getCollection(COLLECTION_NAME, UserVoucher.class);
    }

    @Override
    public List<UserVoucher> findAll() {
        return userVoucherCollection.find().into(new java.util.ArrayList<>());
    }

    @Override
    public Optional<UserVoucher> findById(String id) {
        UserVoucher userVoucher = userVoucherCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        return Optional.ofNullable(userVoucher);
    }

    @Override
    public List<UserVoucher> findByUserId(String userId) {
        return userVoucherCollection.find(Filters.eq("userId", new ObjectId(userId)))
                .into(new java.util.ArrayList<>());
    }

    @Override
    public List<UserVoucher> findByVoucherId(String voucherId) {
        return userVoucherCollection.find(Filters.eq("voucherId", new ObjectId(voucherId)))
                .into(new java.util.ArrayList<>());
    }

    @Override
    public UserVoucher save(UserVoucher userVoucher) {
        if (userVoucher.getId() == null) {
            userVoucher.setId(new ObjectId());
        }
        
        Date now = new Date();
        if (userVoucher.getCreatedAt() == null) {
            userVoucher.setCreatedAt(now);
        }
        userVoucher.setUpdatedAt(now);
        
        userVoucherCollection.insertOne(userVoucher);
        return userVoucher;
    }

    @Override
    public UserVoucher update(UserVoucher userVoucher) {
        userVoucher.setUpdatedAt(new Date());
        
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        
        return userVoucherCollection.findOneAndReplace(
                Filters.eq("_id", userVoucher.getId()),
                userVoucher,
                options);
    }

    @Override
    public void deleteById(String id) {
        userVoucherCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
} 