package com.mongodb.starter.entity;

import java.util.Date;
import java.util.Objects;

import org.bson.types.ObjectId;

public class OrderEntity {
    private ObjectId id;
    private ObjectId userId;
    private String orderAddress;
    private Date orderTime;
    private String paymentMethod;
    private Double orderCost;
    private ObjectId voucherId;
    private String recipientName;
    private String recipientPhone;
    private ObjectId storeId;
    private String orderStatus;
    private Date createdAt;

    public OrderEntity() {
    }

    public OrderEntity(ObjectId id, ObjectId userId, String orderAddress, Date orderTime, String paymentMethod,
            Double orderCost, ObjectId voucherId, String recipientName, String recipientPhone, ObjectId storeId,
            String orderStatus, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.orderAddress = orderAddress;
        this.orderTime = orderTime;
        this.paymentMethod = paymentMethod;
        this.orderCost = orderCost;
        this.voucherId = voucherId;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.storeId = storeId;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
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

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(Double orderCost) {
        this.orderCost = orderCost;
    }

    public ObjectId getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(ObjectId voucherId) {
        this.voucherId = voucherId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public ObjectId getStoreId() {
        return storeId;
    }

    public void setStoreId(ObjectId storeId) {
        this.storeId = storeId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderEntity [id=" + id + ", userId=" + userId + ", orderAddress=" + orderAddress + ", orderTime="
                + orderTime + ", paymentMethod=" + paymentMethod + ", orderCost=" + orderCost + ", voucherId="
                + voucherId + ", recipientName=" + recipientName + ", recipientPhone=" + recipientPhone + ", storeId="
                + storeId + ", orderStatus=" + orderStatus + ", createdAt=" + createdAt + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderAddress, orderTime, paymentMethod, orderCost, voucherId, recipientName,
                recipientPhone, storeId, orderStatus, createdAt);
    }
}
