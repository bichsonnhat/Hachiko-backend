package com.mongodb.starter.usecases.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserNotificationDTO;

@Service
public interface UserNotificationUsecase {
    List<UserNotificationDTO> getAllUserNotifications();
    
    Optional<UserNotificationDTO> getUserNotificationById(String id);
    
    List<UserNotificationDTO> getUserNotificationsByUserId(String userId);
    
    List<UserNotificationDTO> getUserNotificationsByNotificationId(String notificationId);
    
    UserNotificationDTO createUserNotification(UserNotificationDTO userNotificationDTO);
    
    UserNotificationDTO updateUserNotification(String id, UserNotificationDTO userNotificationDTO);
    
    void deleteUserNotification(String id);
} 