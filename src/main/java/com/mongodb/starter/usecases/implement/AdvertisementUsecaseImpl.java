package com.mongodb.starter.usecases.implement;

import java.util.List;

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

    @Override
    public List<AdvertisementEntity> getAdvertisements() {
        return this.advertisementRepository.findAll();
    }

    @Override
    public AdvertisementEntity getAdvertisement(String id) {
        return this.advertisementRepository.findOne(id);
    }

    @Override
    public AdvertisementEntity updateAdvertisement(AdvertisementEntity entity) {
        return this.advertisementRepository.updateOne(entity);
    }

    @Override
    public void deleteAdvertisement(String id) {
        this.advertisementRepository.deleteOne(id);
    }
}