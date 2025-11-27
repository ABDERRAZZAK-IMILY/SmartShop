package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.PaymentDtoRequest;
import com.microtech.smartshop.dto.PaymentDtoResponse;
import com.microtech.smartshop.enums.OrderStatus;
import com.microtech.smartshop.enums.PaymentStatus;
import com.microtech.smartshop.enums.PaymentType;
import com.microtech.smartshop.exception.BusinessException;
import com.microtech.smartshop.exception.ResourceNotFoundException;
import com.microtech.smartshop.mapper.PaymentMapper;
import com.microtech.smartshop.model.Order;
import com.microtech.smartshop.model.Payment;
import com.microtech.smartshop.repository.OrderRepository;
import com.microtech.smartshop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentDtoResponse createPayment(PaymentDtoRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELED || order.getStatus() == OrderStatus.REJECTED) {
            throw new BusinessException("Cannot add payment to a canceled or rejected order.");
        }

        if (order.getRemainingAmount() <= 0) {
            throw new BusinessException("Order is already fully paid.");
        }

        if (request.getAmount() > order.getRemainingAmount()) {
            throw new BusinessException("Amount exceeds remaining balance. Remaining: " + order.getRemainingAmount());
        }

        if (request.getType() == PaymentType.ESPECES && request.getAmount() > 20000) {
            throw new BusinessException("Cash payment cannot exceed 20,000 DH (Legal Limit).");
        }

        PaymentStatus initialStatus;
        LocalDateTime cashingDate = null;

        if (request.getType() == PaymentType.ESPECES) {
            initialStatus = PaymentStatus.ENCAISSE;
            cashingDate = LocalDateTime.now();
        } else {
            initialStatus = PaymentStatus.EN_ATTENTE;
        }

        Payment payment = Payment.builder()
                .order(order)
                .amount(request.getAmount())
                .type(request.getType())
                .status(initialStatus)
                .paymentDate(LocalDateTime.now())
                .cashingDate(cashingDate)
                .paymentNumber(order.getPayments().size() + 1)
                .build();

        order.setRemainingAmount(order.getRemainingAmount() - request.getAmount());

        paymentRepository.save(payment);
        orderRepository.save(order);

        return paymentMapper.toDto(payment);
    }

    @Override
    public List<PaymentDtoResponse> getOrderPayments(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order not found");
        }

        return paymentRepository.findByOrderId(orderId).stream()
                .map(paymentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDtoResponse validatePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        if (payment.getStatus() == PaymentStatus.ENCAISSE) {
            throw new BusinessException("Payment is already cashed.");
        }

        payment.setStatus(PaymentStatus.ENCAISSE);
        payment.setCashingDate(LocalDateTime.now());

        return paymentMapper.toDto(paymentRepository.save(payment));
    }
}