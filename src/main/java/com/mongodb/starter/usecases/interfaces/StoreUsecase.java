package com.mongodb.starter.usecases.interfaces;

import com.mongodb.starter.entity.StoreEntity;

import java.util.List;

public interface StoreUsecase {
    StoreEntity createStore(StoreEntity storeEntity);

    List<StoreEntity> getStores();

    StoreEntity getStore(String id);

    StoreEntity updateStore(StoreEntity entity);

    void deleteStore(String id);
}
