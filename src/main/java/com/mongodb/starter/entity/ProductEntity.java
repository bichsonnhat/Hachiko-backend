package com.mongodb.starter.entity;

import org.bson.types.ObjectId;
import java.util.Objects;

public class ProductEntity {
    private ObjectId id;
    private String description;
    private String imageUrl;
    private double price;
    private String title;
    private ObjectId categoryID;

    public ProductEntity() {}

    public ProductEntity(ObjectId id, String description, String imageUrl, double price, String title, ObjectId categoryID) {
        this.id = id;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.title = title;
        this.categoryID = categoryID;
    }

    public ObjectId getId() {
        return this.id;
    }

    public ProductEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public ProductEntity setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public double getPrice() {
        return this.price;
    }

    public ProductEntity setPrice(double price) {
        this.price = price;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public ProductEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public ObjectId getCategoryID() {
        return this.categoryID;
    }

    public ProductEntity setCategoryID(ObjectId categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", categoryID=" + categoryID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Double.compare(that.price, price) == 0 &&
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(imageUrl, that.imageUrl) &&
            Objects.equals(title, that.title) &&
            Objects.equals(categoryID, that.categoryID);
}

    @Override
    public int hashCode() {
        return Objects.hash(id, description, imageUrl, price, title, categoryID);
    }
}