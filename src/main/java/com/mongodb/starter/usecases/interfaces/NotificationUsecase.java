package com.mongodb.starter.usecases.interfaces;

import java.util.List;
import com.mongodb.starter.entity.NotificationEntity;

public interface NotificationUsecase {
    
    NotificationEntity createNotification(NotificationEntity notificationEntity);
    
    List<NotificationEntity> getAllNotifications();
    
    NotificationEntity getNotification(String id);
    
    NotificationEntity updateNotification(NotificationEntity entity);

    void deleteNotification(String id);
}