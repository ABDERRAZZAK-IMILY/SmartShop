package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.ProductDtorequest;
import com.microtech.smartshop.dto.ProductDtoresponse;
import com.microtech.smartshop.exception.ResourceNotFoundException;
import com.microtech.smartshop.mapper.ProductMapper;
import com.microtech.smartshop.model.Product;
import com.microtech.smartshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDtoresponse createProduct(ProductDtorequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDtoresponse updateProduct(Long id, ProductDtorequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return productMapper.toDto(productRepository.save(product));
    }

    @Override
    public Page<ProductDtoresponse> getAllProducts(Pageable pageable) {
        return productRepository.findByDeletedFalse(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public ProductDtoresponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Soft Delete Logic: Mark as deleted instead of removing from DB
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public Page<ProductDtoresponse> searchProducts(String name, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseAndDeletedFalse(name, pageable)
                .map(productMapper::toDto);
    }
}