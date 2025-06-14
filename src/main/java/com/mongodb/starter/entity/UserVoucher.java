package com.mongodb.starter.entity;

import java.util.Date;
import org.bson.types.ObjectId;

public class UserVoucher {
    private ObjectId id;
    private String userId;
    private ObjectId voucherId;
    private Date createdAt;
    private Date updatedAt;
    private String status;
    
    public UserVoucher() {
    }

    public UserVoucher(ObjectId id, String userId, ObjectId voucherId, Date createdAt, Date updatedAt, String status) {
        this.id = id;
        this.userId = userId;
        this.voucherId = voucherId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
    }

    public ObjectId getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public ObjectId getVoucherId() {
        return voucherId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setVoucherId(ObjectId voucherId) {
        this.voucherId = voucherId;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

