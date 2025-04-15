package com.mongodb.starter.usecases.implement;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.NotificationDTO;
import com.mongodb.starter.repositories.interfaces.NotificationRepository;
import com.mongodb.starter.usecases.interfaces.NotificationUsecase;

@Service
public class NotificationUsecaseImpl implements NotificationUsecase {

    private final NotificationRepository notificationRepository;

    public NotificationUsecaseImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationDTO createNotification(NotificationDTO notificationEntity) {
        return new NotificationDTO(notificationRepository.insertOne(notificationEntity.toNotificationEntity()));
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