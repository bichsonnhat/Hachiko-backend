package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.AdvertisementEntity;

@Repository
public interface AdvertisementRepository {
    AdvertisementEntity insertOne(AdvertisementEntity advertisementEntity);

    List<AdvertisementEntity> findAll();

    AdvertisementEntity findOne(String id);

    AdvertisementEntity updateOne(AdvertisementEntity entity);

    void deleteOne(String id);
}
