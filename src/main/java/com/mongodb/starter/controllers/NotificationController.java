package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mongodb.starter.dtos.NotificationDTO;
import com.mongodb.starter.entity.NotificationEntity;
import com.mongodb.starter.usecases.interfaces.NotificationUsecase;

@RestController
@RequestMapping("notifications")
public class NotificationController {
    private final static Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationUsecase notificationUsecase;

    public NotificationController(NotificationUsecase notificationUsecase) {
        this.notificationUsecase = notificationUsecase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationEntity> getAllNotifications() {
        return notificationUsecase.getAllNotifications();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NotificationEntity getNotification(@PathVariable String id) {
        return notificationUsecase.getNotification(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationEntity createNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationUsecase.createNotification(notificationDTO.toNotificationEntity());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public NotificationEntity updateNotification(@RequestBody NotificationDTO notificationDTO) {
        return notificationUsecase.updateNotification(notificationDTO.toNotificationEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNotification(@PathVariable String id) {
        notificationUsecase.deleteNotification(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}