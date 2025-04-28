package com.mongodb.starter.entity;

import org.bson.types.ObjectId;

import java.util.Objects;

public class StoreEntity {
    private ObjectId id;
    private String name;
    private String address;
    private String imageURL;
    private double longitude ;
    private double latitude;

    public StoreEntity() {
    }

    public StoreEntity(ObjectId  id, String name, String address, String imageURL, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageURL = imageURL;
        this.longitude  = longitude;
        this.latitude = latitude;
    }

    public ObjectId  getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude ;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "StoreEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", longitude=" + longitude  +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreEntity that = (StoreEntity) o;
        return id == that.id && Double.compare(longitude , that.longitude ) == 0 && Double.compare(latitude, that.latitude) == 0 && Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(imageURL, that.imageURL);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, imageURL, longitude , latitude);
    }
}
