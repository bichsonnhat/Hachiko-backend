package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.FavouriteProductEntity;

public record FavouriteProductDTO(
        String id,
        String userId,
        String productId) {
    public FavouriteProductDTO(FavouriteProductEntity favouriteProductEntity) {
        this(favouriteProductEntity.getId() == null ? null : favouriteProductEntity.getId().toHexString(),
                favouriteProductEntity.getUserId() == null ? null : favouriteProductEntity.getUserId().toHexString(),
                favouriteProductEntity.getProductId() == null ? null
                        : favouriteProductEntity.getProductId().toHexString());
    }

    public FavouriteProductEntity toFavouriteProductEntity() {
        return new FavouriteProductEntity(
                id == null ? null : new ObjectId(id),
                userId == null ? null : new ObjectId(userId),
                productId == null ? null : new ObjectId(productId));
    }
}
