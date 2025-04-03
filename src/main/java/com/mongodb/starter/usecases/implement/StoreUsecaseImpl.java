package com.mongodb.starter.usecases.implement;

import com.mongodb.starter.entity.StoreEntity;
import com.mongodb.starter.repositories.interfaces.StoreRepository;
import com.mongodb.starter.usecases.interfaces.StoreUsecase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreUsecaseImpl implements StoreUsecase {
    private final StoreRepository storeRepository;
    public StoreUsecaseImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public StoreEntity createStore(StoreEntity storeEntity) {
        return this.storeRepository.insertOne(storeEntity);
    }

    @Override
    public List<StoreEntity> getStores() {
        return this.storeRepository.findAll();
    }

    @Override
    public StoreEntity getStore(String id) {
        return this.storeRepository.findOne(id);
    }

    @Override
    public StoreEntity updateStore(StoreEntity entity) {
        return this.storeRepository.updateOne(entity);
    }

    @Override
    public void deleteStore(String id) {
        this.storeRepository.deleteOne(id);
    }
}
