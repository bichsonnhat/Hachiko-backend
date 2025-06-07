package com.mongodb.starter.repositories.mongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.starter.entity.VoucherEntity;
import com.mongodb.starter.repositories.interfaces.VoucherRepository;

import jakarta.annotation.PostConstruct;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;

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
    public Optional<VoucherEntity> findById(String id) {
        return Optional.ofNullable(
                voucherCollection.find(eq("_id", new ObjectId(id))).first()
        );
    }
    
    @Override
    public List<VoucherEntity> findByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<ObjectId> objectIds = ids.stream()
                .map(ObjectId::new)
                .collect(Collectors.toList());
        
        return voucherCollection.find(in("_id", objectIds))
                .into(new ArrayList<>());
    }
    
    @Override
    public VoucherEntity save(VoucherEntity voucherEntity) {
        if (voucherEntity.getId() == null) {
            voucherEntity.setId(new ObjectId());
        }
        voucherCollection.insertOne(voucherEntity);
        return voucherEntity;
    }
    
    @Override
    public VoucherEntity update(VoucherEntity voucherEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions()
                .returnDocument(ReturnDocument.AFTER);
        
        return voucherCollection.findOneAndReplace(
                eq("_id", voucherEntity.getId()),
                voucherEntity,
                options);
    }
    
    @Override
    public void deleteById(String id) {
        voucherCollection.deleteOne(eq("_id", new ObjectId(id)));
    }
}
