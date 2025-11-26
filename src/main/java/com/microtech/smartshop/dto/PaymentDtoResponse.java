package com.microtech.smartshop.dto;

import com.microtech.smartshop.enums.PaymentStatus;
import com.microtech.smartshop.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PaymentDtoResponse {
    private Long id;
    private int paymentNumber;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private LocalDateTime cashingDate;
}