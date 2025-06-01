package com.mongodb.starter.entity;

import org.bson.types.ObjectId;
import java.util.Date;
import java.util.Objects;

public class ReviewEntity {
    private ObjectId id;
    private String userId;
    private ObjectId productId;
    private int rating;
    private String comment;
    private Date createdAt;
    private Date updatedAt;

    public ReviewEntity() {
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public ReviewEntity(ObjectId id, String userId, ObjectId productId, int rating, String comment, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt != null ? createdAt : new Date();
        this.updatedAt = updatedAt != null ? updatedAt : new Date();
    }

    public ObjectId getId() {
        return this.id;
    }

    public ReviewEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return this.userId;
    }

    public ReviewEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public ObjectId getProductId() {
        return this.productId;
    }

    public ReviewEntity setProductId(ObjectId productId) {
        this.productId = productId;
        return this;
    }

    public int getRating() {
        return this.rating;
    }

    public ReviewEntity setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public String getComment() {
        return this.comment;
    }

    public ReviewEntity setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }

    public ReviewEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public ReviewEntity setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public String toString() {
        return "ReviewEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", productId=" + productId +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewEntity that = (ReviewEntity) o;
        return rating == that.rating &&
                Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, rating, comment, createdAt, updatedAt);
    }
}