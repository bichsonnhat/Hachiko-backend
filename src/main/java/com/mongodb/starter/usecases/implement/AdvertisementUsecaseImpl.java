package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.AdvertisementDTO;
import com.mongodb.starter.repositories.interfaces.AdvertisementRepository;
import com.mongodb.starter.usecases.interfaces.AdvertisementUsecase;

@Service
public class AdvertisementUsecaseImpl implements AdvertisementUsecase {
    private final AdvertisementRepository advertisementRepository;
    
    public AdvertisementUsecaseImpl(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public AdvertisementDTO createAdvertisement(AdvertisementDTO advertisementEntity) {
        return new AdvertisementDTO(advertisementRepository.insertOne(advertisementEntity.toAdvertisementEntity()));
    }

    @Override
    public List<AdvertisementDTO> getAdvertisements() {
        return this.advertisementRepository.findAll().stream().map(AdvertisementDTO::new).toList();
    }

    @Override
    public AdvertisementDTO getAdvertisement(String id) {
        return new AdvertisementDTO(this.advertisementRepository.findOne(id));
    }

    @Override
    public AdvertisementDTO updateAdvertisement(AdvertisementDTO entity) {
        return new AdvertisementDTO(this.advertisementRepository.updateOne(entity.toAdvertisementEntity()));
    }

    @Override
    public void deleteAdvertisement(String id) {
        this.advertisementRepository.deleteOne(id);
    }
}