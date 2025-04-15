package com.mongodb.starter.entity;

import org.bson.types.ObjectId;
import java.util.Objects;

public class NotificationEntity {
    private ObjectId id;
    private String description;
    private String imageUrl;
    private String title;
    private String date;

    public NotificationEntity() {}

    public NotificationEntity(ObjectId id, String description, String imageUrl, String title, String date) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.title = title;
        this.date = date;
    }

    public ObjectId getId() {
        return this.id;
    }

    public NotificationEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public NotificationEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public NotificationEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public NotificationEntity setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getDate() {
        return this.date;
    }

    public NotificationEntity setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    NotificationEntity that = (NotificationEntity) o;
    return Objects.equals(id, that.id) &&
           Objects.equals(description, that.description) &&
           Objects.equals(imageUrl, that.imageUrl) &&
           Objects.equals(title, that.title) &&
           Objects.equals(date, that.date);
}

    @Override
    public int hashCode() {
        return Objects.hash(id, description, imageUrl, title, date);
    }
}