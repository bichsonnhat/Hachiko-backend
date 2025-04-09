package com.mongodb.starter.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.starter.dtos.OrderRequestDTO;
import com.mongodb.starter.dtos.OrderResponseDTO;
import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.type.CreatePaymentLinkRequestBody;
import com.mongodb.starter.usecases.interfaces.OrderUsecase;

import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    private final OrderUsecase orderUsecase;

    private final PayOS payOS;

    public OrderController(PayOS payOS, OrderUsecase orderUsecase) {
        super();
        this.payOS = payOS;
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

    @PostMapping(path = "/payos/create")
    public ObjectNode createPaymentLink(@RequestBody CreatePaymentLinkRequestBody RequestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            final String productName = RequestBody.getProductName();
            final String description = RequestBody.getDescription();
            final String returnUrl = RequestBody.getReturnUrl();
            final String cancelUrl = RequestBody.getCancelUrl();
            final int price = RequestBody.getPrice();
            // Gen order code
            String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
            long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

            ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();

            PaymentData paymentData = PaymentData.builder().orderCode(orderCode).description(description).amount(price)
                    .item(item).returnUrl(returnUrl).cancelUrl(cancelUrl).build();

            CheckoutResponseData data = payOS.createPaymentLink(paymentData);

            response.put("error", 0);
            response.put("message", "success");
            response.set("data", objectMapper.valueToTree(data));
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", "fail");
            response.set("data", null);
            return response;

        }
    }

    @PostMapping(path = "/confirm-webhook")
    public ObjectNode confirmWebhook(@RequestBody Map<String, String> requestBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();
        try {
            String str = payOS.confirmWebhook(requestBody.get("webhookUrl"));
            response.set("data", objectMapper.valueToTree(str));
            response.put("error", 0);
            response.put("message", "ok");
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", -1);
            response.put("message", e.getMessage());
            response.set("data", null);
            return response;
        }
    }
}
