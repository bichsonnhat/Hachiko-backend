package com.mongodb.starter.repositories.interfaces;

import com.mongodb.starter.entity.StoreEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository {
    StoreEntity insertOne(StoreEntity storeEntity);
    void deleteOne(String id);
    List<StoreEntity> findAll();
    StoreEntity findOne(String id);
    StoreEntity updateOne(StoreEntity entity);


}
