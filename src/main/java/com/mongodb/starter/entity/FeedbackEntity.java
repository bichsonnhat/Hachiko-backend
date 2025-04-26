package com.mongodb.starter.entity;

import org.bson.types.ObjectId;

public class FeedbackEntity {
    private ObjectId id;
    private ObjectId userId;
    private String username;
    private String feedbackContent;

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

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public FeedbackEntity() {
    }

    public FeedbackEntity(ObjectId id, ObjectId userId, String username, String feedbackContent) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.feedbackContent = feedbackContent;
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
        result = prime * result + ((feedbackContent == null) ? 0 : feedbackContent.hashCode());
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
        FeedbackEntity other = (FeedbackEntity) obj;
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
        if (feedbackContent == null) {
            if (other.feedbackContent != null)
                return false;
        } else if (!feedbackContent.equals(other.feedbackContent))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "FeedbackEntity [id=" + id + ", userId=" + userId + ", username=" + username + ", feedbackContent="
                + feedbackContent + "]";
    }

}
