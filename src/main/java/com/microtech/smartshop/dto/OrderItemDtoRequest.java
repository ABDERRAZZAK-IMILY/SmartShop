package com.microtech.smartshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrderItemDtoRequest {
    @NotNull(message = "L'ID du produit est obligatoire")
    private Long productId;

    @Min(value = 1, message = "La quantité doit être au moins 1")
    private int quantity;
}