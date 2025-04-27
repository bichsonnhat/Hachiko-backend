package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.OrderItemEntity;

public record OrderItemDTO(
        String id,
        String orderId,
        String productId,
        String productImage,
        String productName,
        String topping,
        Integer quantity,
        Double price,
        String note,
        String size) {
    public OrderItemDTO(OrderItemEntity orderItemEntity) {
        this(orderItemEntity.getId() == null ? null : orderItemEntity.getId().toHexString(),
                orderItemEntity.getOrderId() == null ? null : orderItemEntity.getOrderId().toHexString(),
                orderItemEntity.getProductId() == null ? null : orderItemEntity.getProductId().toHexString(),
                orderItemEntity.getProductImage(), orderItemEntity.getProductName(), orderItemEntity.getTopping(),
                orderItemEntity.getQuantity(), orderItemEntity.getPrice(),
                orderItemEntity.getNote(), orderItemEntity.getSize());
    }

    public OrderItemEntity toOrderItemEntity() {
        return new OrderItemEntity(id == null ? null : new ObjectId(id),
                productId == null ? null : new ObjectId(productId),
                productImage, productName,
                orderId == null ? null : new ObjectId(orderId), quantity, size, topping,
                note, price);
    }
}
