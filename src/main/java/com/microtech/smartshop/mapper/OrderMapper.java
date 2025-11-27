package com.microtech.smartshop.mapper;

import com.microtech.smartshop.dto.OrderDtoResponse;
import com.microtech.smartshop.dto.OrderItemDtoResponse;
import com.microtech.smartshop.model.Order;
import com.microtech.smartshop.model.OrderItem;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDtoResponse toDto(Order order) {
        if (order == null) return null;
        return OrderDtoResponse.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .clientName(order.getClient() != null ? order.getClient().getFullName() : "Unknown")
                .subTotal(order.getSubTotal())
                .discountAmount(order.getDiscountAmount())
                .taxAmount(order.getTaxAmount())
                .totalAmount(order.getTotalAmount())
                .remainingAmount(order.getRemainingAmount())
                .status(order.getStatus())
                .items(order.getOrderItems().stream()
                        .map(this::toItemDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private OrderItemDtoResponse toItemDto(OrderItem item) {
        return OrderItemDtoResponse.builder()
                .id(item.getId())
                .productName(item.getProduct() != null ? item.getProduct().getName() : "Unknown")
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .totalPrice(item.getTotalLine())
                .build();
    }
}