package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import com.mongodb.starter.dtos.NotificationDTO;

public interface NotificationUsecase {
    
    NotificationDTO createNotification(NotificationDTO notificationEntity);
    
    List<NotificationDTO> getAllNotifications();
    
    NotificationDTO getNotification(String id);
    
    NotificationDTO updateNotification(NotificationDTO entity);

    void deleteNotification(String id);
}