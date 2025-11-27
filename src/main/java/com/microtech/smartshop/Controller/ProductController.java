package com.microtech.smartshop.Controller;
import com.microtech.smartshop.dto.ProductDtoresponse;
import com.microtech.smartshop.dto.ProductDtorequest;
import com.microtech.smartshop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;



    @PostMapping
    public ResponseEntity<ProductDtoresponse> createProduct(@Valid @RequestBody ProductDtorequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDtoresponse>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDtoresponse>> getProductByName(@RequestParam String search, Pageable pageable) {
        return ResponseEntity.ok(productService.searchProducts(search, pageable));

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDtoresponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDtoresponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDtorequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}