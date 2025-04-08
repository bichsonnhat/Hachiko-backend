package com.mongodb.starter.dtos;

import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.starter.entity.OrderEntity;

public record OrderDTO(
        String id,
        String userId,
        String orderAddress,
        Date orderTime,
        String paymentMethod,
        Double orderCost,
        String voucherId,
        String recipientName,
        String recipientPhone,
        String storeId,
        String orderStatus,
        Date createdAt) {
    public OrderDTO(OrderEntity orderEntity) {
        this(orderEntity.getId() == null ? null : orderEntity.getId().toHexString(),
                orderEntity.getUserId() == null ? null : orderEntity.getUserId().toHexString(),
                orderEntity.getOrderAddress(), orderEntity.getOrderTime(), orderEntity.getPaymentMethod(),
                orderEntity.getOrderCost(),
                orderEntity.getVoucherId() == null ? null : orderEntity.getVoucherId().toHexString(),
                orderEntity.getRecipientName(), orderEntity.getRecipientPhone(),
                orderEntity.getStoreId() == null ? null : orderEntity.getStoreId().toHexString(),
                orderEntity.getOrderStatus(), orderEntity.getCreatedAt());
    }

    public OrderEntity toOrderEntity() {
        return new OrderEntity(id == null ? null : new ObjectId(id),
                userId == null ? null : new ObjectId(userId), orderAddress, orderTime, paymentMethod,
                orderCost, voucherId == null ? null : new ObjectId(voucherId), recipientName,
                recipientPhone, storeId == null ? null : new ObjectId(storeId), orderStatus, createdAt);
    }
}