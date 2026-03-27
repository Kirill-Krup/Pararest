package com.actisys.orderservice.dto;

import com.actisys.enums.PaymentType;
import com.actisys.orderservice.model.OrderStatus;
import com.actisys.orderservice.model.OrderType;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashMap;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderDTO {
    private HashMap<Long, Integer> items;
    private OrderType orderType;
    private BigDecimal totalPrice;
    private PaymentType paymentType;
    private String userRequest;
}
