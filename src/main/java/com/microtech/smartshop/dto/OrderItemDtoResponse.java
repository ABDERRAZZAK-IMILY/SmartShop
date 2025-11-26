package com.microtech.smartshop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDtoResponse {


    private Long id;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalPrice;

}
