package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.OrderEntity;

@Repository
public interface OrderRepository {
    OrderEntity insertOne(OrderEntity orderEntity);

    void deleteOne(String id);

    OrderEntity updateOne(OrderEntity entity);

    OrderEntity findById(String id);

    List<OrderEntity> findAll();

    List<OrderEntity> findAllByUserId(String customerId);
}
