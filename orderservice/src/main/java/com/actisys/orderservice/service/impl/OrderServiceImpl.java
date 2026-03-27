package com.actisys.orderservice.service.impl;

import com.actisys.dto.UpdatePaymentStatus;
import com.actisys.enums.PaymentStatus;
import com.actisys.orderservice.dto.CreateOrderDTO;
import com.actisys.orderservice.dto.OrderDTO;
import com.actisys.orderservice.mapper.OrderInfoMapper;
import com.actisys.orderservice.mapper.OrderMapper;
import com.actisys.orderservice.model.Order;
import com.actisys.orderservice.model.OrderInfo;
import com.actisys.orderservice.model.OrderStatus;
import com.actisys.orderservice.repository.OrderInfoRepository;
import com.actisys.orderservice.repository.OrderRepository;
import com.actisys.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = repository.findAll();
        return orders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = repository.findById(id).orElseThrow(RuntimeException::new);
        return orderMapper.toDTO(order);
    }

    @Override
    public OrderDTO createNewOrder(UUID userId, CreateOrderDTO createOrderDTO) {
        Order order = Order.builder()
                .userId(userId)
                .status(OrderStatus.PENDING)
                .type(createOrderDTO.getOrderType())
                .orderTime(LocalDateTime.now())
                .totalPrice(createOrderDTO.getTotalPrice())
                .paymentStatus(PaymentStatus.NOT_PAID)
                .paymentType(createOrderDTO.getPaymentType())
                .userRequest(createOrderDTO.getUserRequest())
                .build();
        List<OrderInfo> items = new ArrayList<>();
        for(Map.Entry<Long, Integer> item : createOrderDTO.getItems().entrySet()){
            OrderInfo orderInfo = OrderInfo.builder()
                    .order(order)
                    .menuItemId(item.getKey())
                    .quantity(item.getValue())
                    .build();
            items.add(orderInfo);
        }
        order.setItems(items);
        return orderMapper.toDTO(repository.save(order));
    }

    @Override
    public void updatePaymentStatus(UpdatePaymentStatus updatePaymentStatus) {

    }

    @Override
    public void deleteOrder(Long id) {
        Order order = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.delete(order);
    }

    @Override
    public List<OrderDTO> getAllUserOrders(UUID id) {
        List<Order> allUserOrders = repository.findAllByUserId(id);
        return allUserOrders.stream().map(orderMapper::toDTO).collect(Collectors.toList());
    }
}
