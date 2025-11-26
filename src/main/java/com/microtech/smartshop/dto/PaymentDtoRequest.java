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
    @NotNull(message = "L'ID de la commande est obligatoire")
    private Long orderId;

    @Positive(message = "Le montant doit être positif")
    private double amount;

    @NotNull(message = "Le type de paiement est obligatoire")
    private PaymentType type;
}