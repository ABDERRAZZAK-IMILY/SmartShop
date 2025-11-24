package com.microtech.smartshop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDtorequest {

    private String name;
    private double price;
    private int stock;
    private boolean deleted = false;

}
