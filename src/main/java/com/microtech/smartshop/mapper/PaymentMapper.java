package com.microtech.smartshop.mapper;

import com.microtech.smartshop.dto.PaymentDtoResponse;
import com.microtech.smartshop.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentDtoResponse toDto(Payment payment) {
        if (payment == null) return null;
        return PaymentDtoResponse.builder()
                .id(payment.getId())
                .paymentNumber(payment.getPaymentNumber())
                .amount(payment.getAmount())
                .type(payment.getType())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .cashingDate(payment.getCashingDate())
                .build();
    }
}