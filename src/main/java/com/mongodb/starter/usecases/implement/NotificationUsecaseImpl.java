package com.mongodb.starter.usecases.implement;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mongodb.starter.entity.NotificationEntity;
import com.mongodb.starter.repositories.interfaces.NotificationRepository;
import com.mongodb.starter.usecases.interfaces.NotificationUsecase;

@Service
public class NotificationUsecaseImpl implements NotificationUsecase {

    private final NotificationRepository notificationRepository;

    public NotificationUsecaseImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public NotificationEntity createNotification(NotificationEntity notificationEntity) {
        return notificationRepository.insertOne(notificationEntity);
    }

    @Override
    public List<NotificationEntity> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public NotificationEntity getNotification(String id) {
        return notificationRepository.findOne(id);
    }

    @Override
    public NotificationEntity updateNotification(NotificationEntity entity) {
        return notificationRepository.updateOne(entity);
    }

    @Override
    public void deleteNotification(String id) {
        notificationRepository.deleteOne(id);
    }
}