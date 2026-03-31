package com.actisys.orderservice.controller;

import com.actisys.dto.UpdatePaymentStatus;
import com.actisys.orderservice.dto.CreateOrderDTO;
import com.actisys.orderservice.dto.OrderDTO;
import com.actisys.orderservice.model.OrderStatus;
import com.actisys.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestHeader("X-User-Id") String userId,
                                                @RequestBody CreateOrderDTO createOrderDTO){
        return ResponseEntity.ok(orderService.createNewOrder(UUID.fromString(userId), createOrderDTO));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable String userId){
        return ResponseEntity.ok(orderService.getAllUserOrders(UUID.fromString(userId)));
    }

    @GetMapping("/cart")
    public ResponseEntity<OrderDTO> getCart(@RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok(orderService.getCart(UUID.fromString(userId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/cart/items/{menuItemId}")
    public ResponseEntity<Void> addDishToCart(@PathVariable Long menuItemId,
                                              @RequestHeader("X-User-Id") String userId){
        orderService.addDishToCart(menuItemId, UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart/items/{menuItemId}")
    public ResponseEntity<Void> deleteDishFromCart(@PathVariable Long menuItemId,
                                                   @RequestHeader("X-User-Id") String userId){
        orderService.deleteDishFromCart(menuItemId, UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancelOrder/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Id") String userId){
        orderService.clearCart(UUID.fromString(userId));
        return ResponseEntity.ok().build();
    }
}