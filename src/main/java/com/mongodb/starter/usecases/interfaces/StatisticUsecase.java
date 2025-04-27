package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.DashboardResponseDTO;
import com.mongodb.starter.dtos.OrderDTO;
import com.mongodb.starter.dtos.ProductStatisticResponseDTO;
import com.mongodb.starter.dtos.RevenueResponseDTO;

@Service
public interface StatisticUsecase {
    DashboardResponseDTO getDashboardStatistics();

    List<OrderDTO> getOrderStatistic(String orderStatus, long startDate, long endDate);

    List<ProductStatisticResponseDTO> getPopularProducts();

    RevenueResponseDTO getRevenue(long startDate, long endDate);
}
