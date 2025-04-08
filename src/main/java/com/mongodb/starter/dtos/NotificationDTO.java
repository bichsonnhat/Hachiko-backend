package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;
import com.mongodb.starter.entity.NotificationEntity;

public record NotificationDTO(
    String id,
    String description,
    String imageUrl,
    String title,
    String date
) {
    public NotificationDTO(NotificationEntity notificationEntity) {
        this(
            notificationEntity.getId() == null ? 
                new ObjectId().toHexString() : 
                notificationEntity.getId().toHexString(),
            notificationEntity.getDescription(),
            notificationEntity.getImageUrl(),
            notificationEntity.getTitle(),
            notificationEntity.getDate()
        );
    }

    public NotificationEntity toNotificationEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new NotificationEntity(
            _id,
            description,
            imageUrl,
            title,
            date
        );
    }
}