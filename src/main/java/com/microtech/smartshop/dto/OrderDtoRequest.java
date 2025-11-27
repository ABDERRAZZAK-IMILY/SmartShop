package com.microtech.smartshop.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderDtoRequest {
    @NotNull(message = "Id is required")
    private Long clientId;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    private List<OrderItemDtoRequest> items;

    @Pattern(regexp = "PROMO-[A-Z0-9]{4}", message = "Promo code format is invalid")
    private String promoCode;
}