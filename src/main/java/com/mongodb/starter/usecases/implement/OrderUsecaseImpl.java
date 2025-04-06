package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.OrderResponseDTO;
import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.entity.OrderItemEntity;
import com.mongodb.starter.repositories.interfaces.OrderItemRepository;
import com.mongodb.starter.repositories.interfaces.OrderRepository;
import com.mongodb.starter.usecases.interfaces.OrderUsecase;

@Service
public class OrderUsecaseImpl implements OrderUsecase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderUsecaseImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderEntity orderEntity, List<OrderItemEntity> orderItemEntities) {
        OrderEntity createdOrder = orderRepository.insertOne(orderEntity);
        List<OrderItemEntity> createdOrderItems = orderItemRepository.bulkInsert(orderItemEntities);
        return new OrderResponseDTO(createdOrder, createdOrderItems);
    }

    @Override
    public void deleteOrder(String id) {
        orderRepository.deleteOne(id);
        orderItemRepository.deleteAllByOrderId(id);
    }

    @Override
    public OrderResponseDTO getOrder(String id) {
        OrderEntity orderEntity = orderRepository.findById(id);
        List<OrderItemEntity> orderItems = orderItemRepository.findAllByOrderId(id);
        return new OrderResponseDTO(orderEntity, orderItems);
    }

    @Override
    public List<OrderEntity> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId) {
        List<OrderEntity> orders = orderRepository.findAllByCustomerId(customerId);
        return orders.stream()
                .map(order -> new OrderResponseDTO(order,
                        orderItemRepository.findAllByOrderId(order.getId().toHexString())))
                .toList();
    }

    @Override
    public OrderResponseDTO updateOrder(OrderEntity orderEntity) {
        OrderEntity updatedOrder = orderRepository.updateOne(orderEntity);
        List<OrderItemEntity> orderItems = orderItemRepository.findAllByOrderId(orderEntity.getId().toHexString());
        return new OrderResponseDTO(updatedOrder, orderItems);
    }

}
