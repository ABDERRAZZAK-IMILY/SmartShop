package com.microtech.smartshop.dto;

import com.microtech.smartshop.enums.PaymentType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class PaymentDtoRequest {
    @NotNull(message = "Id is required")
    private Long orderId;

    @Positive(message = "amount must be positive")
    private double amount;

    @NotNull(message = "payment type is required")
    private PaymentType type;
}