package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;
import com.mongodb.starter.entity.ReviewEntity;
import java.util.Date;

public record ReviewDTO(
    String id,
    String userId,
    String productId,
    int rating,
    String comment,
    Date createdAt,
    Date updatedAt
) {
    public ReviewDTO(ReviewEntity reviewEntity) {
        this(
            reviewEntity.getId() == null ? 
                new ObjectId().toHexString() : 
                reviewEntity.getId().toHexString(),
            reviewEntity.getUserId(),
            reviewEntity.getProductId() == null ?
                null :
                reviewEntity.getProductId().toHexString(),
            reviewEntity.getRating(),
            reviewEntity.getComment(),
            reviewEntity.getCreatedAt(),
            reviewEntity.getUpdatedAt()
        );
    }

    public ReviewEntity toReviewEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        ObjectId _productId = productId == null ? null : new ObjectId(productId);
        return new ReviewEntity(
            _id,
            userId,
            _productId,
            rating,
            comment,
            createdAt,
            updatedAt
        );
    }
}