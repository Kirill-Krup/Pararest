package com.actisys.orderservice.service.impl;

import com.actisys.enums.PaymentType;
import com.actisys.orderservice.dto.CreateOrderDTO;
import com.actisys.orderservice.dto.OrderDTO;
import com.actisys.orderservice.mapper.OrderMapper;
import com.actisys.orderservice.model.Order;
import com.actisys.orderservice.model.OrderStatus;
import com.actisys.orderservice.model.OrderType;
import com.actisys.orderservice.repository.OrderInfoRepository;
import com.actisys.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderInfoRepository orderInfoRepository;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createNewOrder_withCardPayment_sendsPaymentEvent() {
        UUID userId = UUID.randomUUID();
        Order cart = Order.builder().id(7L).userId(userId).status(OrderStatus.IN_CART).build();
        CreateOrderDTO dto = CreateOrderDTO.builder()
                .orderType(OrderType.IN_RESTAURANT)
                .paymentType(PaymentType.CARD)
                .totalPrice(new BigDecimal("100.00"))
                .userRequest("No onions")
                .build();
        OrderDTO mapped = OrderDTO.builder().id(7L).build();

        when(orderRepository.findByUserIdAndStatus(userId, OrderStatus.IN_CART)).thenReturn(Optional.of(cart));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));
        when(orderMapper.toDTO(any(Order.class))).thenReturn(mapped);

        OrderDTO result = orderService.createNewOrder(userId, dto);

        assertEquals(7L, result.getId());
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("CREATE_ORDER_PAYMENT_EVENT"), any());
    }
}
