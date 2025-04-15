package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import com.mongodb.starter.dtos.AdvertisementDTO;

public interface AdvertisementUsecase {
    AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementEntity);

    List<AdvertisementDTO> getAdvertisements();

    AdvertisementDTO getAdvertisement(String id);

    AdvertisementDTO updateAdvertisement(AdvertisementDTO entity);

    void deleteAdvertisement(String id);
}
