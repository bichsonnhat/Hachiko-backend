package com.mongodb.starter.entity;

import java.util.Objects;

import org.bson.types.ObjectId;

public class OrderItemEntity {
    private ObjectId id;
    private ObjectId productId;
    private ObjectId orderId;
    private Integer quantity;
    private String size;
    private String topping;
    private String note;
    private Double price;

    public OrderItemEntity() {
    }

    public OrderItemEntity(ObjectId id, ObjectId productId, ObjectId orderId, Integer quantity, String size,
            String topping, String note, Double price) {
        this.id = id;
        this.productId = productId;
        this.orderId = orderId;
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
        this.note = note;
        this.price = price;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public ObjectId getOrderId() {
        return orderId;
    }

    public void setOrderId(ObjectId orderId) {
        this.orderId = orderId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTopping() {
        return topping;
    }

    public void setTopping(String topping) {
        this.topping = topping;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemEntity [id=" + id + ", productId=" + productId + ", orderId=" + orderId + ", quantity="
                + quantity + ", size=" + size + ", topping=" + topping + ", note=" + note + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, orderId, quantity, size, topping, note, price);
    }
}
