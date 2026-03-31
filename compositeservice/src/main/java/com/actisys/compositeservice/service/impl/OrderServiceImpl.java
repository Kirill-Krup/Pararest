package com.actisys.compositeservice.service.impl;

import com.actisys.compositeservice.client.OrderServiceClient;
import com.actisys.compositeservice.dto.order.FullOrderDTO;
import com.actisys.compositeservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderServiceClient orderServiceClient;

    @Override
    public Mono<ResponseEntity<List<FullOrderDTO>>> getFullOrders(String id) {
        return null;
    }
}
