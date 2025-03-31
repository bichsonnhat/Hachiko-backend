package com.mongodb.starter.usecases.interfaces;

import com.mongodb.starter.entity.AdvertisementEntity;

public interface AdvertisementUsecase {
    AdvertisementEntity createAdvertisement(AdvertisementEntity advertisementEntity);
}
