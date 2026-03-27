package com.actisys.compositeservice.service.impl;

import com.actisys.compositeservice.dto.order.FullOrderDTO;
import com.actisys.compositeservice.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {



    @Override
    public List<FullOrderDTO> getFullOrders(String id) {

        return List.of();
    }
}
