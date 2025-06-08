package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.NotificationDTO;
import com.mongodb.starter.entity.NotificationEntity;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.entity.UserNotification;
import com.mongodb.starter.repositories.interfaces.NotificationRepository;
import com.mongodb.starter.repositories.interfaces.UserRepository;
import com.mongodb.starter.repositories.interfaces.UserNotificationRepository;
import com.mongodb.starter.usecases.interfaces.NotificationUsecase;

@Service
public class NotificationUsecaseImpl implements NotificationUsecase {

    private final NotificationRepository notificationRepository;
    private final UserNotificationRepository userNotificationRepository;
    private final UserRepository userRepository;

    public NotificationUsecaseImpl(NotificationRepository notificationRepository, UserNotificationRepository userNotificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationEntity) {
        NotificationEntity notification = notificationEntity.toNotificationEntity();
        notificationRepository.insertOne(notification);
        
        // Create user-notification for all user
        List<UserEntity> users = userRepository.findAll();
        for (UserEntity user : users) {
            UserNotification userNotification = new UserNotification();
            userNotification.setUserId(user.getId());
            userNotification.setNotificationId(notification.getId());
            userNotification.setIsSeen(false);
            userNotification.setCreatedAt(new Date());
            userNotification.setUpdatedAt(new Date());
            userNotificationRepository.save(userNotification);
        }
        return new NotificationDTO(notification);
    }

    @Override
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(NotificationDTO::new).toList();
    }

    @Override
    public NotificationDTO getNotification(String id) {
        return new NotificationDTO(notificationRepository.findOne(id));
    }

    @Override
    public NotificationDTO updateNotification(NotificationDTO entity) {
        return new NotificationDTO(notificationRepository.updateOne(entity.toNotificationEntity()));
    }

    @Override
    public void deleteNotification(String id) {
        notificationRepository.deleteOne(id);
    }
}