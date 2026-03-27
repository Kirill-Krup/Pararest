package com.actisys.compositeservice.service;

import com.actisys.compositeservice.dto.order.FullOrderDTO;
import java.util.List;

public interface OrderService {
    List<FullOrderDTO> getFullOrders(String id);
}
