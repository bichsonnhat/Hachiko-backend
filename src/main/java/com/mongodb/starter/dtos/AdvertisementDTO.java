package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.AdvertisementEntity;

public record AdvertisementDTO(
    String id,
    String description,
    String imageUrl
) {
    public AdvertisementDTO(AdvertisementEntity advertisementEntity){
        this(advertisementEntity.getId() == null ? new ObjectId().toHexString() : advertisementEntity.getId().toHexString(),
        advertisementEntity.getDescription(), advertisementEntity.getImageUrl());
    }

    public AdvertisementEntity toAdvertisementEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new AdvertisementEntity(_id, description, imageUrl);
    }
}
