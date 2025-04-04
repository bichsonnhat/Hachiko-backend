package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;
import com.mongodb.starter.entity.ProductEntity;

public record ProductDTO(
    String id,
    String description,
    String imageUrl,
    double price,
    String title,
    String categoryID
) {
    public ProductDTO(ProductEntity productEntity) {
        this(
            productEntity.getId() == null ? 
                new ObjectId().toHexString() : 
                productEntity.getId().toHexString(),
            productEntity.getDescription(),
            productEntity.getImageUrl(),
            productEntity.getPrice(),
            productEntity.getTitle(),
            productEntity.getCategoryID() == null ?
                null :
                productEntity.getCategoryID().toHexString()
        );
    }

    public ProductEntity toProductEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        ObjectId _categoryId = categoryID == null ? null : new ObjectId(categoryID);
        return new ProductEntity(
            _id,
            description,
            imageUrl,
            price,
            title,
            _categoryId
        );
    }
}