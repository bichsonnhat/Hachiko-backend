package com.mongodb.starter.usecases.interfaces;

import com.mongodb.starter.dtos.StoreDTO;
import com.mongodb.starter.entity.StoreEntity;

import java.util.List;

public interface StoreUsecase {
    StoreDTO createStore(StoreEntity storeEntity);

    List<StoreDTO> getStores();

    StoreDTO getStore(String id);

    StoreDTO updateStore(StoreEntity entity);

    void deleteStore(String id);
}
