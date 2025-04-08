package com.mongodb.starter.entity;

import java.util.Objects;

import org.bson.types.ObjectId;

public class AdvertisementEntity {
    private ObjectId id;
    private String description;

    public AdvertisementEntity() {}

    public AdvertisementEntity(ObjectId id, String description){
        this.id = id;
        this.description = description;
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public AdvertisementEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public AdvertisementEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public String toString() {
        return "AdvertismentEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override 
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
