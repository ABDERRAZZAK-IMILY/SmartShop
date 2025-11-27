package com.microtech.smartshop.Controller;

import com.microtech.smartshop.dto.PaymentDtoRequest;
import com.microtech.smartshop.dto.PaymentDtoResponse;
import com.microtech.smartshop.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDtoResponse> createPayment(@Valid @RequestBody PaymentDtoRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentDtoResponse>> getOrderPayments(@PathVariable Long orderId) {
        return ResponseEntity.ok(paymentService.getOrderPayments(orderId));
    }

    @PatchMapping("/{id}/validate")
    public ResponseEntity<PaymentDtoResponse> validatePayment(@PathVariable Long id) {
        return ResponseEntity.ok(paymentService.validatePayment(id));
    }
}