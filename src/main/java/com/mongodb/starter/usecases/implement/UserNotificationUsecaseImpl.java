package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserNotificationDTO;
import com.mongodb.starter.entity.UserNotification;
import com.mongodb.starter.repositories.interfaces.UserNotificationRepository;
import com.mongodb.starter.usecases.interfaces.UserNotificationUsecase;

@Service
public class UserNotificationUsecaseImpl implements UserNotificationUsecase {
    private final UserNotificationRepository userNotificationRepository;

    public UserNotificationUsecaseImpl(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    @Override
    public List<UserNotificationDTO> getAllUserNotifications() {
        return userNotificationRepository.findAll().stream()
                .map(UserNotificationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserNotificationDTO> getUserNotificationById(String id) {
        return userNotificationRepository.findById(id)
                .map(UserNotificationDTO::new);
    }

    @Override
    public List<UserNotificationDTO> getUserNotificationsByUserId(String userId) {
        return userNotificationRepository.findByUserId(userId).stream()
                .map(UserNotificationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserNotificationDTO> getUserNotificationsByNotificationId(String notificationId) {
        return userNotificationRepository.findByNotificationId(notificationId).stream()
                .map(UserNotificationDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserNotificationDTO createUserNotification(UserNotificationDTO userNotificationDTO) {
        UserNotification userNotification = userNotificationDTO.toUserNotification();
        userNotification.setId(null); // Ensure we're creating a new document
        UserNotification savedUserNotification = userNotificationRepository.save(userNotification);
        return new UserNotificationDTO(savedUserNotification);
    }

    @Override
    public UserNotificationDTO updateUserNotification(String id, UserNotificationDTO userNotificationDTO) {
        // Verify the UserNotification exists
        userNotificationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserNotification not found with id: " + id));
        
        UserNotification userNotification = userNotificationDTO.toUserNotification();
        userNotification.setId(new ObjectId(id));
        
        UserNotification updatedUserNotification = userNotificationRepository.update(userNotification);
        return new UserNotificationDTO(updatedUserNotification);
    }

    @Override
    public void deleteUserNotification(String id) {
        userNotificationRepository.deleteById(id);
    }
    
    @Override
    public Long countUnseenNotificationsByUserId(String userId) {
        return userNotificationRepository.countByUserIdAndIsSeen(userId, false);
    }
} 