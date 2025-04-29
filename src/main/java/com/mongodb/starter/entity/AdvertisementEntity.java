package com.mongodb.starter.entity;

import java.util.Objects;

import org.bson.types.ObjectId;

public class AdvertisementEntity {
    private ObjectId id;
    private String description;
    private String imageUrl;

    public AdvertisementEntity() {}

    public AdvertisementEntity(ObjectId id, String description, String imageUrl) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public AdvertisementEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public AdvertisementEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public AdvertisementEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public String toString() {
        return "AdvertismentEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvertisementEntity that = (AdvertisementEntity) o;
            return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description);
}

    @Override 
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
