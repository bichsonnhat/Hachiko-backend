package com.mongodb.starter.repositories.interfaces;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.AdvertisementEntity;

@Repository
public interface AdvertisementRepository {
    AdvertisementEntity insertOne(AdvertisementEntity advertisementEntity);
}
