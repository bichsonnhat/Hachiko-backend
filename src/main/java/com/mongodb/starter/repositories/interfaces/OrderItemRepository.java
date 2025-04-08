package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.OrderItemEntity;

@Repository
public interface OrderItemRepository {
    List<OrderItemEntity> bulkInsert(List<OrderItemEntity> orderItems);

    List<OrderItemEntity> findAllByOrderId(String orderId);

    void deleteAllByOrderId(String orderId);
}
