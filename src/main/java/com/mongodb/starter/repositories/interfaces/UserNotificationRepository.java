package com.mongodb.starter.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.UserNotification;

@Repository
public interface UserNotificationRepository {
    List<UserNotification> findAll();
    
    Optional<UserNotification> findById(String id);
    
    List<UserNotification> findByUserId(String userId);
    
    List<UserNotification> findByNotificationId(String notificationId);
    
    UserNotification save(UserNotification userNotification);
    
    UserNotification update(UserNotification userNotification);
    
    void deleteById(String id);
    
    Long countByUserIdAndIsSeen(String userId, Boolean isSeen);

} 