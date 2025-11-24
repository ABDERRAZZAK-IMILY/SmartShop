package com.microtech.smartshop.mapper;

import com.microtech.smartshop.dto.ProductDtorequest;
import com.microtech.smartshop.dto.ProductDtoresponse;
import com.microtech.smartshop.model.Product;

public class ProductMapper {

    public Product toEntity(ProductDtorequest dto){

        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .deleted(dto.isDeleted())
                .build();
    }

    public ProductDtoresponse toDto(Product entity){
        return  ProductDtoresponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .stock(entity.getStock())
                .deleted(entity.isDeleted())
                .build();
    }



}
