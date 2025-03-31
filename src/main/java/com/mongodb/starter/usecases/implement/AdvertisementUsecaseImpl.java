package com.mongodb.starter.usecases.implement;

import com.mongodb.starter.entity.AdvertisementEntity;
import com.mongodb.starter.repositories.interfaces.AdvertisementRepository;
import com.mongodb.starter.usecases.interfaces.AdvertisementUsecase;

public class AdvertisementUsecaseImpl implements AdvertisementUsecase {
    private final AdvertisementRepository advertisementRepository;
    
    public AdvertisementUsecaseImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public AdvertisementEntity createAdvertisement(AdvertisementEntity advertisementEntity) {
        return advertisementRepository.insertOne(advertisementEntity);
    }
}