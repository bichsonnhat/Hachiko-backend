package com.mongodb.starter.usecases.implement;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.DashboardResponseDTO;
import com.mongodb.starter.dtos.OrderDTO;
import com.mongodb.starter.dtos.ProductStatisticResponseDTO;
import com.mongodb.starter.dtos.RevenueResponseDTO;
import com.mongodb.starter.entity.OrderEntity;
import com.mongodb.starter.entity.OrderItemEntity;
import com.mongodb.starter.entity.ProductEntity;
import com.mongodb.starter.repositories.interfaces.OrderItemRepository;
import com.mongodb.starter.repositories.interfaces.OrderRepository;
import com.mongodb.starter.repositories.interfaces.ProductRepository;
import com.mongodb.starter.usecases.interfaces.StatisticUsecase;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatisticUsecaseImpl implements StatisticUsecase {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public StatisticUsecaseImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Override
    public DashboardResponseDTO getDashboardStatistics() {
        List<OrderEntity> orders = orderRepository.findAll();
        int numberOfCompletedOrders = (int) orders.stream().filter(order -> order.getOrderStatus().equals("COMPLETED"))
                .count();
        int numberOfPendingOrders = (int) orders.stream().filter(order -> order.getOrderStatus().equals("PENDING"))
                .count();
        int numberOfCancelledOrders = (int) orders.stream().filter(order -> order.getOrderStatus().equals("CANCELLED"))
                .count();

        double totalRevenue = orders.stream()
                .filter(order -> order.getOrderStatus().equals("COMPLETED"))
                .mapToDouble(OrderEntity::getOrderCost).sum();

        return new DashboardResponseDTO(
                numberOfCompletedOrders,
                numberOfPendingOrders,
                numberOfCancelledOrders,
                totalRevenue);
    }

    @Override
    public List<OrderDTO> getOrderStatistic(String orderStatus, long startDate, long endDate) {
        List<OrderEntity> orders = orderRepository.findAll();

        return orders.stream()
                .filter(order -> order.getOrderStatus().equals(orderStatus))
                .filter(order -> order.getOrderTime().getTime() >= startDate)
                .filter(order -> order.getOrderTime().getTime() <= endDate)
                .map(order -> new OrderDTO(order))
                .toList();
    }

    @Override
    public List<ProductStatisticResponseDTO> getPopularProducts() {
        List<OrderEntity> orders = orderRepository.findAll();

        orders = orders.stream()
                .filter(order -> order.getOrderStatus().equals("COMPLETED"))
                .collect(Collectors.toList());

        Map<String, Integer> productSales = new HashMap<>();

        for (OrderEntity order : orders) {
            List<OrderItemEntity> orderItems = orderItemRepository.findAllByOrderId(order.getId().toHexString());

            for (OrderItemEntity orderItem : orderItems) {
                String productId = orderItem.getProductId().toHexString();
                int quantity = orderItem.getQuantity();

                productSales.put(productId, productSales.getOrDefault(productId, 0) + quantity);
            }
        }

        List<ProductStatisticResponseDTO> result = productSales.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(5)
                .map(entry -> {
                    ProductEntity product = productRepository.findOne(entry.getKey());

                    if (product != null) {
                        return new ProductStatisticResponseDTO(
                                entry.getKey(),
                                product.getTitle(),
                                product.getImageUrl(),
                                entry.getValue());
                    }

                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public RevenueResponseDTO getRevenue(long startDate, long endDate) {
        List<OrderEntity> orders = orderRepository.findAll();

        Map<YearMonth, Double> monthlyRevenue = new TreeMap<>();

        for (OrderEntity order : orders) {
            if (order.getOrderStatus().equals("COMPLETED") &&
                    order.getOrderTime().getTime() >= startDate &&
                    order.getOrderTime().getTime() <= endDate) {

                YearMonth yearMonth = YearMonth.from(order.getOrderTime().toInstant().atZone(ZoneId.systemDefault()));

                monthlyRevenue.put(yearMonth, monthlyRevenue.getOrDefault(yearMonth, 0.0) + order.getOrderCost());
            }
        }

        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        for (Map.Entry<YearMonth, Double> entry : monthlyRevenue.entrySet()) {
            labels.add(entry.getKey().toString());
            data.add(entry.getValue());
        }

        return new RevenueResponseDTO(labels, data);
    }

}
