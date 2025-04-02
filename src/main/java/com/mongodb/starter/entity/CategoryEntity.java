package com.mongodb.starter.entity;

import java.util.Objects;

import org.bson.types.ObjectId;

public class CategoryEntity {
    private ObjectId id;
    private String name;
    private String imgUrl;

    public CategoryEntity() {
    }

    public CategoryEntity(ObjectId id, String name, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "CategoryEntity [id=" + id + ", name=" + name + ", imgUrl=" + imgUrl + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imgUrl);
    }
}
