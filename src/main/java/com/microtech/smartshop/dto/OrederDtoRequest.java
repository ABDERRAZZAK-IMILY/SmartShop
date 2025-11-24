package com.microtech.smartshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class OrederDtoRequest {
    private Long clientId;
    private List<OrederitemDtorequest> items;
    private String promoCode;
}