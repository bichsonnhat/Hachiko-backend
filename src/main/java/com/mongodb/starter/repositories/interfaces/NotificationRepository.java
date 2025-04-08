package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.NotificationEntity;

@Repository
public interface NotificationRepository {
    
    NotificationEntity insertOne(NotificationEntity notificationEntity);
    
    void deleteOne(String id);
    
    List<NotificationEntity> findAll();
    
    NotificationEntity findOne(String id);
    
    NotificationEntity updateOne(NotificationEntity entity);
}