package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import com.mongodb.starter.entity.AdvertisementEntity;

public interface AdvertisementUsecase {
    AdvertisementEntity createAdvertisement(AdvertisementEntity advertisementEntity);

    List<AdvertisementEntity> getAdvertisements();

    AdvertisementEntity getAdvertisement(String id);

    AdvertisementEntity updateAdvertisement(AdvertisementEntity entity);

    void deleteAdvertisement(String id);
}
