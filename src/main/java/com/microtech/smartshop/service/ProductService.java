package com.microtech.smartshop.service;
import com.microtech.smartshop.dto.ProductDtoresponse;
import com.microtech.smartshop.dto.ProductDtorequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDtoresponse createProduct(ProductDtorequest request);
    ProductDtoresponse updateProduct(Long id, ProductDtorequest request);
    Page<ProductDtoresponse> getAllProducts(Pageable pageable);
    ProductDtoresponse getProductById(Long id);
    void deleteProduct(Long id);

    Page<ProductDtoresponse> searchProducts(String search,Pageable pageable);
}