package com.mongodb.starter.entity;

import java.util.Date;
import java.util.Objects;
import org.bson.types.ObjectId;


public class VoucherEntity {
    private ObjectId id;
    private ObjectId userId;
    private String title;
    private String description;
    private String imgUrl;
    private int discountPrice;
    private double discountPercent;
    private boolean isFreeShip;
    private int minOrderPrice;
    private int minOrderItem;
    private String type;
    private Date expiryDate;

    public VoucherEntity() {
    }

    public VoucherEntity(ObjectId id, ObjectId userId, String title, String description, String imgUrl, int discountPrice, double discountPercent, boolean isFreeShip, int minOrderPrice, int minOrderItem, String type, Date expiryDate) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
        this.discountPrice = discountPrice;
        this.discountPercent = discountPercent;
        this.isFreeShip = isFreeShip;
        this.minOrderPrice = minOrderPrice;
        this.minOrderItem = minOrderItem;
        this.type = type;
        this.expiryDate = expiryDate;
    }

    public ObjectId getId() {
        return id;
    }

    public ObjectId getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public boolean isFreeShip() {
        return isFreeShip;
    }

    public int getMinOrderPrice() {
        return minOrderPrice;
    }

    public int getMinOrderItem() {
        return minOrderItem;
    }

    public String getType() {
        return type;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUserId(ObjectId userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setIsFreeShip(boolean isFreeShip) {
        this.isFreeShip = isFreeShip;
    }

    public void setMinOrderPrice(int minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public void setMinOrderItem(int minOrderItem) {
        this.minOrderItem = minOrderItem;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "VoucherEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", discountPrice=" + discountPrice +
                ", discountPercent=" + discountPercent +
                ", isFreeShip=" + isFreeShip +
                ", minOrderPrice=" + minOrderPrice +
                ", minOrderItem=" + minOrderItem +
                ", type='" + type + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoucherEntity that = (VoucherEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(discountPrice, that.discountPrice) &&
                Objects.equals(discountPercent, that.discountPercent) &&
                Objects.equals(isFreeShip, that.isFreeShip) &&
                Objects.equals(minOrderPrice, that.minOrderPrice) &&
                Objects.equals(minOrderItem, that.minOrderItem) &&
                Objects.equals(type, that.type) &&
                Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, description, imgUrl, discountPrice, discountPercent, isFreeShip, minOrderPrice, minOrderItem, type, expiryDate);
    }
}
