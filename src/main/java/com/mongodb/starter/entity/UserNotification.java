package com.mongodb.starter.entity;

import java.util.Date;
import org.bson.types.ObjectId;

public class UserNotification {
    private ObjectId id;
    private String userId;
    private ObjectId notificationId;
    private Boolean isSeen;
    private Date createdAt;
    private Date updatedAt;
    
    public UserNotification() {
    }

    public UserNotification(ObjectId id, String userId, ObjectId notificationId, Boolean isSeen, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.notificationId = notificationId;
        this.isSeen = isSeen;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ObjectId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public ObjectId getNotificationId() {
        return notificationId;
    }

    public Boolean getIsSeen() {
        return isSeen;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setNotificationId(ObjectId notificationId) {
        this.notificationId = notificationId;
    }

    public void setIsSeen(Boolean isSeen) {
        this.isSeen = isSeen;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
} 