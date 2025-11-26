package com.microtech.smartshop.dto;

import com.microtech.smartshop.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDtoResponse {
    private Long id;
    private LocalDateTime createdAt;
    private String clientName;
    private double subTotal;
    private double discountAmount;
    private double taxAmount;
    private double totalAmount;
    private double remainingAmount;
    private OrderStatus status;
    private List<OrderItemDtoResponse> items;
}