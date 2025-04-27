package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.OrderDTO;
import com.mongodb.starter.dtos.OrderItemDTO;
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

        orderItemEntities.forEach(orderItemEntity -> orderItemEntity.setOrderId(createdOrder.getId()));
        List<OrderItemEntity> createdOrderItems = orderItemRepository.bulkInsert(orderItemEntities);

        List<OrderItemDTO> responseOrderItems = createdOrderItems.stream()
                .map((orderItem) -> new OrderItemDTO(orderItem)).collect(Collectors.toList());

        return new OrderResponseDTO(new OrderDTO(createdOrder), responseOrderItems);
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

        List<OrderItemDTO> responseOrderItems = orderItems.stream()
                .map((orderItem) -> new OrderItemDTO(orderItem)).collect(Collectors.toList());

        return new OrderResponseDTO(new OrderDTO(orderEntity), responseOrderItems);
    }

    @Override
    public List<OrderDTO> getOrders() {
        List<OrderEntity> orderEntities = orderRepository.findAll();

        List<OrderDTO> orderDTOs = orderEntities.stream()
                .map((orderEntity) -> new OrderDTO(orderEntity)).collect(Collectors.toList());

        return orderDTOs;
    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(String userId, String orderStatus) {
        List<OrderEntity> orders = orderRepository.findAllByUserId(userId);

        if (orderStatus != null) {
            orders = orders.stream().filter(order -> order.getOrderStatus().equalsIgnoreCase(orderStatus)).toList();
        }

        List<OrderResponseDTO> orderResponseDTOs = orders.stream().map(order -> {
            List<OrderItemEntity> orderItemEntities = orderItemRepository.findAllByOrderId(order.getId().toHexString());

            List<OrderItemDTO> responseOrderItems = orderItemEntities.stream()
                    .map((orderItem) -> new OrderItemDTO(orderItem)).collect(Collectors.toList());

            return new OrderResponseDTO(new OrderDTO(order), responseOrderItems);
        }).toList();

        return orderResponseDTOs;
    }

    @Override
    public OrderResponseDTO updateOrder(OrderEntity orderEntity) {
        OrderEntity updatedOrder = orderRepository.updateOne(orderEntity);
        List<OrderItemEntity> orderItems = orderItemRepository.findAllByOrderId(orderEntity.getId().toHexString());

        List<OrderItemDTO> responseOrderItems = orderItems.stream()
                .map((orderItem) -> new OrderItemDTO(orderItem)).collect(Collectors.toList());

        return new OrderResponseDTO(new OrderDTO(updatedOrder), responseOrderItems);
    }

}
