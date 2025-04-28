package com.mongodb.starter.dtos;

import com.mongodb.starter.entity.StoreEntity;
import org.bson.types.ObjectId;

public record StoreDTO(
        String id,
        String name,
        String address,
        String imageURL,
        double longitude,
        double latitude
) {
    public StoreDTO(StoreEntity storeEntity){
        this(
                storeEntity.getId() == null ? new ObjectId().toHexString() : storeEntity.getId().toHexString(),
                storeEntity.getName(),
                storeEntity.getAddress(),
                storeEntity.getImageURL(),
                storeEntity.getLongitude(),
                storeEntity.getLatitude()
        );
    }
    public StoreEntity toStoreEntity(){
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new StoreEntity(_id, name, address, imageURL, longitude, latitude);
    }
}
