package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.OrderRequestDTO;
import com.mongodb.starter.dtos.OrderResponseDTO;
import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.usecases.interfaces.OrderUsecase;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderUsecase orderUsecase;

    public OrderController(OrderUsecase orderUsecase) {
        this.orderUsecase = orderUsecase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderEntity> getAllOrders() {
        return orderUsecase.getOrders();
    }

    @GetMapping("/customer/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponseDTO> getOrdersByUserId(@PathVariable String userId) {
        LOGGER.info("Get orders by userId id: {}", userId);
        return orderUsecase.getOrdersByCustomerId(userId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO getOrder(@PathVariable String id) {
        LOGGER.info("Get order by id: {}", id);
        return orderUsecase.getOrder(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderUsecase.createOrder(
                orderRequestDTO.order().toOrderEntity(),
                orderRequestDTO.orderItems().stream()
                        .map(orderItem -> orderItem.toOrderItemEntity())
                        .toList());
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDTO updateOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        return orderUsecase.updateOrder(orderRequestDTO.order().toOrderEntity());
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String id) {
        LOGGER.info("Delete order by id: {}", id);
        orderUsecase.deleteOrder(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
