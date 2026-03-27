package com.actisys.orderservice.service;

import com.actisys.dto.UpdatePaymentStatus;
import com.actisys.orderservice.dto.CreateOrderDTO;
import com.actisys.orderservice.dto.OrderDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(Long id);

    OrderDTO createNewOrder(UUID userId, CreateOrderDTO createOrderDTO);

    void updatePaymentStatus(UpdatePaymentStatus updatePaymentStatus);

    void deleteOrder(Long id);

    List<OrderDTO> getAllUserOrders(UUID id);
}
