package com.actisys.orderservice.controller;

import com.actisys.dto.UpdatePaymentStatus;
import com.actisys.orderservice.dto.CreateOrderDTO;
import com.actisys.orderservice.dto.OrderDTO;
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

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDTO> createNewOrder(@RequestHeader("X-User-Id") String userId,
                                                   @RequestBody CreateOrderDTO createOrderDTO){
        return ResponseEntity.ok(orderService.createNewOrder(UUID.fromString(userId),createOrderDTO));
    }

    @GetMapping("/getOrdersByUserId/{id}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable String id){
        return ResponseEntity.ok().body(orderService.getAllUserOrders(UUID.fromString(id)));
    }

    @PutMapping("/updatePaymentStatus")
    public ResponseEntity<Void> updatePaymentStatus(@RequestBody UpdatePaymentStatus updatePaymentStatus){
        orderService.updatePaymentStatus(updatePaymentStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }


}
