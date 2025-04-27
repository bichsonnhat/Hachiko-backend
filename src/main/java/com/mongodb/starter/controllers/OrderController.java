package com.mongodb.starter.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.starter.dtos.OrderDTO;
import com.mongodb.starter.dtos.OrderRequestDTO;
import com.mongodb.starter.dtos.OrderResponseDTO;
import com.mongodb.starter.type.CreatePaymentLinkRequestBody;
import com.mongodb.starter.usecases.interfaces.OrderUsecase;

import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@RestController
@RequestMapping("/orders")
@Tag(name = "orders")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class OrderController {
        private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
        private final OrderUsecase orderUsecase;
        private final PayOS payOS;

        public OrderController(PayOS payOS, OrderUsecase orderUsecase) {
                super();
                this.payOS = payOS;
                this.orderUsecase = orderUsecase;
        }

        @Operation(summary = "Get all orders", description = "Retrieves a list of all orders in the system", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all orders")
        @GetMapping
        @ResponseStatus(HttpStatus.OK)
        public List<OrderDTO> getAllOrders() {
                return orderUsecase.getOrders();
        }

        @Operation(summary = "Get orders by customer ID", description = "Retrieves all orders associated with a specific customer", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved customer orders"),
                        @ApiResponse(responseCode = "404", description = "Customer not found")
        })
        @GetMapping("/customer/{userId}")
        @ResponseStatus(HttpStatus.OK)
        public List<OrderResponseDTO> getOrdersByUserId(
                        @Parameter(description = "ID of the customer to retrieve orders for") @PathVariable String userId,
                        @RequestParam(required = false) String orderStatus) {
                LOGGER.info("Get orders by userId id: {}", userId);
                return orderUsecase.getOrdersByCustomerId(userId, orderStatus);
        }

        @Operation(summary = "Get order by ID", description = "Retrieves a specific order using its ID", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the order"),
                        @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @GetMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        public OrderResponseDTO getOrder(
                        @Parameter(description = "ID of the order to retrieve") @PathVariable String id) {
                LOGGER.info("Get order by id: {}", id);
                return orderUsecase.getOrder(id);
        }

        @Operation(summary = "Create a new order", description = "Creates a new order with the provided order details and items", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Order successfully created"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping("")
        @ResponseStatus(HttpStatus.CREATED)
        public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
                return orderUsecase.createOrder(
                                orderRequestDTO.order().toOrderEntity(),
                                orderRequestDTO.orderItems().stream()
                                                .map(orderItem -> orderItem.toOrderItemEntity())
                                                .toList());
        }

        @Operation(summary = "Update an existing order", description = "Updates an existing order with new information", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Order successfully updated"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @PutMapping("")
        @ResponseStatus(HttpStatus.OK)
        public OrderResponseDTO updateOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
                return orderUsecase.updateOrder(orderRequestDTO.order().toOrderEntity());
        }

        @Operation(summary = "Delete an order", description = "Deletes an order by its ID", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Order successfully deleted"),
                        @ApiResponse(responseCode = "404", description = "Order not found")
        })
        @DeleteMapping("/delete/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteOrder(
                        @Parameter(description = "ID of the order to delete") @PathVariable String id) {
                LOGGER.info("Delete order by id: {}", id);
                orderUsecase.deleteOrder(id);
        }

        @Operation(summary = "Create PayOS payment link", description = "Creates a payment link using PayOS payment gateway", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Payment link successfully created"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "500", description = "Payment gateway error")
        })
        @PostMapping(path = "/payos")
        public ObjectNode createPaymentLink(@RequestBody CreatePaymentLinkRequestBody RequestBody) {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode response = objectMapper.createObjectNode();
                try {
                        final String productName = RequestBody.getProductName();
                        final String description = RequestBody.getDescription();
                        final String returnUrl = RequestBody.getReturnUrl();
                        final String cancelUrl = RequestBody.getCancelUrl();
                        final int price = RequestBody.getPrice();

                        String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
                        long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));

                        ItemData item = ItemData.builder().name(productName).price(price).quantity(1).build();

                        PaymentData paymentData = PaymentData.builder()
                                        .orderCode(orderCode)
                                        .description(description)
                                        .amount(price)
                                        .item(item)
                                        .returnUrl(returnUrl)
                                        .cancelUrl(cancelUrl)
                                        .build();

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

        @Operation(summary = "Confirm PayOS webhook", description = "Confirms and processes PayOS payment webhook notifications", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Webhook successfully confirmed"),
                        @ApiResponse(responseCode = "400", description = "Invalid webhook data"),
                        @ApiResponse(responseCode = "500", description = "Webhook processing error")
        })
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

        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public final Exception handleAllExceptions(RuntimeException e) {
                LOGGER.error("Internal server error.", e);
                return e;
        }
}