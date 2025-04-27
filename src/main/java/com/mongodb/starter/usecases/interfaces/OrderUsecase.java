package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.OrderDTO;
import com.mongodb.starter.dtos.OrderResponseDTO;
import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.entity.OrderItemEntity;

@Service
public interface OrderUsecase {
    OrderResponseDTO createOrder(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities);

    void deleteOrder(String id);

    List<OrderDTO> getOrders();

    OrderResponseDTO getOrder(String id);

    OrderResponseDTO updateOrder(OrderEntity orderEntity);

    List<OrderResponseDTO> getOrdersByCustomerId(String customerId, String orderStatus);
}
