package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.*;
import com.microtech.smartshop.enums.CustomerTier;
import com.microtech.smartshop.enums.OrderStatus;
import com.microtech.smartshop.exception.BusinessException;
import com.microtech.smartshop.exception.ResourceNotFoundException;
import com.microtech.smartshop.mapper.OrderMapper;
import com.microtech.smartshop.model.*;
import com.microtech.smartshop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;

    private static final double TVA_RATE = 0.20;

    @Override
    @Transactional
    public OrderDtoResponse createOrder(OrderDtoRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        Order order = Order.builder()
                .client(client)
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING)
                .orderItems(new ArrayList<>())
                .build();

        double subTotal = 0.0;

        for (OrderItemDtoRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemRequest.getProductId()));

            if (product.isDeleted()) {
                throw new BusinessException("Product is no longer available: " + product.getName());
            }

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new BusinessException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .totalLine(product.getPrice() * itemRequest.getQuantity())
                    .build();

            order.getOrderItems().add(orderItem);
            subTotal += orderItem.getTotalLine();

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);
        }

        order.setSubTotal(subTotal);

        double discountAmount = calculateDiscount(client, subTotal, request.getPromoCode());
        order.setDiscountAmount(discountAmount);
        order.setPromoCode(request.getPromoCode());

        double amountAfterDiscount = subTotal - discountAmount;
        double taxAmount = amountAfterDiscount * TVA_RATE;
        double totalAmount = amountAfterDiscount + taxAmount;

        order.setTaxAmount(taxAmount);
        order.setTotalAmount(totalAmount);
        order.setRemainingAmount(totalAmount);



        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    private double calculateDiscount(Client client, double subTotal, String promoCode) {
        double discount = 0.0;

        if (client.getTier() == CustomerTier.SILVER && subTotal >= 500) {
            discount += subTotal * 0.05;
        } else if (client.getTier() == CustomerTier.GOLD && subTotal >= 800) {
            discount += subTotal * 0.10;
        } else if (client.getTier() == CustomerTier.PLATINUM && subTotal >= 1200) {
            discount += subTotal * 0.15;
        }

        if (promoCode != null && promoCode.matches("PROMO-[A-Z0-9]{4}")) {
            discount += subTotal * 0.05;
        }

        return discount;
    }

    @Override
    public OrderDtoResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDtoResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDtoResponse> getClientOrders(Long clientId) {
        return orderRepository.findByClientId(clientId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDtoResponse updateStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));


        if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.REJECTED) {
            throw new BusinessException("Cannot change status of an already canceled or rejected order.");
        }

        if (newStatus == OrderStatus.CANCELED || newStatus == OrderStatus.REJECTED) {
            if (order.getStatus() != OrderStatus.PENDING) {
                throw new BusinessException("Only PENDING orders can be canceled or rejected to restore stock.");
            }

            for (OrderItem item : order.getOrderItems()) {
                Product product = item.getProduct();
                product.setStock(product.getStock() + item.getQuantity());
                productRepository.save(product);
            }
        }


        if (newStatus == OrderStatus.CONFIRMED) {
            if (order.getRemainingAmount() > 0) {
                throw new BusinessException("Cannot confirm order. Remaining amount is not zero: " + order.getRemainingAmount());
            }

            Client client = order.getClient();
            client.setTotalOrders(client.getTotalOrders() + 1);
            client.setTotalSpent(client.getTotalSpent() + order.getTotalAmount());

            if (client.getFirstOrderDate() == null) {
                client.setFirstOrderDate(LocalDateTime.now());
            }
            client.setLastOrderDate(LocalDateTime.now());


            updateClientTier(client);

            clientRepository.save(client);
        }

        order.setStatus(newStatus);
        return orderMapper.toDto(orderRepository.save(order));
    }

    private void updateClientTier(Client client) {
        double spent = client.getTotalSpent();
        int orders = client.getTotalOrders();

        if (orders >= 20 || spent >= 15000) {
            client.setTier(CustomerTier.PLATINUM);
        } else if (orders >= 10 || spent >= 5000) {
            client.setTier(CustomerTier.GOLD);
        } else if (orders >= 3 || spent >= 1000) {
            client.setTier(CustomerTier.SILVER);
        } else {
            client.setTier(CustomerTier.BASIC);
        }
    }

}