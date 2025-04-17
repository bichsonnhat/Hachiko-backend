package com.mongodb.starter.usecases.implement;

import com.mongodb.starter.dtos.StoreDTO;
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
    public StoreDTO createStore(StoreEntity storeEntity) {
        StoreEntity newStore = this.storeRepository.insertOne(storeEntity);
        return new StoreDTO(newStore);
    }

    @Override
    public List<StoreDTO> getStores() {
        List<StoreDTO> stores = this.storeRepository.findAll().stream()
                .map(StoreDTO::new)
                .toList();

        return stores;
    }

    @Override
    public StoreDTO getStore(String id) {
        StoreEntity store = this.storeRepository.findOne(id);
        return store == null ? null : new StoreDTO(store);
    }

    @Override
    public StoreDTO updateStore(StoreEntity entity) {
        StoreEntity updatedStore = this.storeRepository.updateOne(entity);
        if (updatedStore == null) {
            return null;
        }
        return new StoreDTO(updatedStore);
    }

    @Override
    public void deleteStore(String id) {
        this.storeRepository.deleteOne(id);
    }
}
