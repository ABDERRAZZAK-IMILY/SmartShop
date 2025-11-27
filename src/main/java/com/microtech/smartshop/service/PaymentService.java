package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.PaymentDtoRequest;
import com.microtech.smartshop.dto.PaymentDtoResponse;
import com.microtech.smartshop.enums.PaymentStatus;
import java.util.List;

public interface PaymentService {
    PaymentDtoResponse createPayment(PaymentDtoRequest request);
    List<PaymentDtoResponse> getOrderPayments(Long orderId);
    PaymentDtoResponse validatePayment(Long paymentId);
}