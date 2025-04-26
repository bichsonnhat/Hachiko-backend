package com.mongodb.starter.entity;

import org.bson.types.ObjectId;

public class FeedbackOrderEntity {
    private ObjectId id;
    private ObjectId userId;
    private String username;
    private ObjectId orderId;
    private String feedbackContent;
    private int rating;

    public FeedbackOrderEntity() {
    }

    public FeedbackOrderEntity(ObjectId id, ObjectId userId, String username, ObjectId orderId, String feedbackContent,
            int rating) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.orderId = orderId;
        this.feedbackContent = feedbackContent;
        this.rating = rating;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public void setOrderId(ObjectId orderId) {
        this.orderId = orderId;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((feedbackContent == null) ? 0 : feedbackContent.hashCode());
        result = prime * result + rating;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FeedbackOrderEntity other = (FeedbackOrderEntity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (orderId == null) {
            if (other.orderId != null)
                return false;
        } else if (!orderId.equals(other.orderId))
            return false;
        if (feedbackContent == null) {
            if (other.feedbackContent != null)
                return false;
        } else if (!feedbackContent.equals(other.feedbackContent))
            return false;
        if (rating != other.rating)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FeedbackOrderEntity [id=" + id + ", userId=" + userId + ", username=" + username + ", orderId="
                + orderId + ", feedbackContent=" + feedbackContent + ", rating=" + rating + "]";
    }

}
