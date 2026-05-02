package com.actisys.analyticsservice.service.impl;

import com.actisys.analyticsservice.client.InventoryServiceClient;
import com.actisys.analyticsservice.client.OrderServiceClient;
import com.actisys.analyticsservice.client.PaymentServiceClient;
import com.actisys.analyticsservice.dto.AnalyticsDTO;
import com.actisys.analyticsservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

    private final PaymentServiceClient paymentServiceClient;
    private final InventoryServiceClient inventoryServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Override
    public Mono<AnalyticsDTO> getMonthlyAnalytics() {
        log.info("Fetching monthly analytics from all services");

        Mono<BigDecimal> revenueMono = paymentServiceClient.getTotalAmount()
                .onErrorResume(e -> {
                    log.error("Failed to fetch revenue, defaulting to 0: {}", e.getMessage());
                    return Mono.just(BigDecimal.ZERO);
                });

        Mono<BigDecimal> expensesMono = inventoryServiceClient.getTotalSuppliesAmount()
                .onErrorResume(e -> {
                    log.error("Failed to fetch expenses, defaulting to 0: {}", e.getMessage());
                    return Mono.just(BigDecimal.ZERO);
                });

        Mono<Long> orderCountMono = orderServiceClient.getTotalCount()
                .onErrorResume(e -> {
                    log.error("Failed to fetch order count, defaulting to 0: {}", e.getMessage());
                    return Mono.just(0L);
                });

        // Все три запроса летят параллельно
        return Mono.zip(revenueMono, expensesMono, orderCountMono)
                .map(tuple -> {
                    BigDecimal revenue = tuple.getT1();
                    BigDecimal expenses = tuple.getT2();
                    Long orderCount = tuple.getT3();

                    BigDecimal netProfit = revenue.subtract(expenses);
                    BigDecimal averageCheck = orderCount > 0
                            ? revenue.divide(BigDecimal.valueOf(orderCount), 2, RoundingMode.HALF_UP)
                            : BigDecimal.ZERO;

                    log.info("Analytics assembled: revenue={}, expenses={}, netProfit={}, orders={}",
                            revenue, expenses, netProfit, orderCount);

                    return AnalyticsDTO.builder()
                            .totalRevenue(revenue)
                            .totalExpenses(expenses)
                            .netProfit(netProfit)
                            .totalOrders(orderCount)
                            .averageCheck(averageCheck)
                            .build();
                });
    }
}