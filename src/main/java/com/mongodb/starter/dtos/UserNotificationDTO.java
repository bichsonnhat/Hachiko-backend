package com.mongodb.starter.dtos;

import java.util.Date;

import com.mongodb.starter.entity.UserNotification;

public record UserNotificationDTO(
        String id,
        String userId,
        String notificationId,
        Boolean isSeen,
        Date createdAt,
        Date updatedAt) {
    
    public UserNotificationDTO(UserNotification userNotification) {
        this(
            userNotification.getId() == null ? null : userNotification.getId().toHexString(),
            userNotification.getUserId(),
            userNotification.getNotificationId() == null ? null : userNotification.getNotificationId().toHexString(),
            userNotification.getIsSeen(),
            userNotification.getCreatedAt(),
            userNotification.getUpdatedAt()
        );
    }

    public UserNotification toUserNotification() {
        return new UserNotification(
            id == null ? null : new org.bson.types.ObjectId(id),
            userId,
            notificationId == null ? null : new org.bson.types.ObjectId(notificationId),
            isSeen,
            createdAt,
            updatedAt
        );
    }
} 