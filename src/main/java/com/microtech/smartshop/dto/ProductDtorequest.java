package com.microtech.smartshop.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDtorequest {


    @NotBlank(message = "name is required")
    private String name;


    @NotNull(message = "price is required")
    private double price;

    @NotNull(message = "stock is required")
    private int stock;

    private boolean deleted = false;

}
