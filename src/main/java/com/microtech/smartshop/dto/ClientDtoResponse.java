package com.microtech.smartshop.dto;

import com.microtech.smartshop.enums.CustomerTier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class ClientDtoResponse {
    private Long id;
    private String fullName;
    private String email;
    private CustomerTier tier;
    private double totalSpent;
    private int totalOrders;
}