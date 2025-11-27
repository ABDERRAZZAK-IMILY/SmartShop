package com.microtech.smartshop.Controller;

import com.microtech.smartshop.dto.OrderDtoRequest;
import com.microtech.smartshop.dto.OrderDtoResponse;
import com.microtech.smartshop.enums.OrderStatus;
import com.microtech.smartshop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDtoResponse> createOrder(@Valid @RequestBody OrderDtoRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderDtoResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDtoResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<OrderDtoResponse>> getClientOrders(@PathVariable Long clientId) {
        return ResponseEntity.ok(orderService.getClientOrders(clientId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDtoResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateStatus(id, status));
    }
}