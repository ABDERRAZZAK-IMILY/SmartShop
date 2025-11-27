package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.OrderDtoRequest;
import com.microtech.smartshop.dto.OrderDtoResponse;
import com.microtech.smartshop.enums.OrderStatus;
import java.util.List;

public interface OrderService {
    OrderDtoResponse createOrder(OrderDtoRequest request);
    OrderDtoResponse getOrderById(Long id);
    List<OrderDtoResponse> getAllOrders();
    List<OrderDtoResponse> getClientOrders(Long clientId);
    OrderDtoResponse updateStatus(Long orderId, OrderStatus newStatus);
}