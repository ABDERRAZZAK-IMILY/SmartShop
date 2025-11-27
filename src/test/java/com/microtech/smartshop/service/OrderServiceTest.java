package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.OrderDtoRequest;
import com.microtech.smartshop.dto.OrderDtoResponse;
import com.microtech.smartshop.dto.OrderItemDtoRequest;
import com.microtech.smartshop.enums.CustomerTier;
import com.microtech.smartshop.mapper.OrderMapper;
import com.microtech.smartshop.model.Client;
import com.microtech.smartshop.model.Order;
import com.microtech.smartshop.model.Product;
import com.microtech.smartshop.repository.ClientRepository;
import com.microtech.smartshop.repository.OrderRepository;
import com.microtech.smartshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private ProductRepository productRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private OrderMapper orderMapper;

    @InjectMocks private OrderServiceImpl orderService;

    @Test
    void shouldApplySilverDiscount() {
        // Arrange
        Client client = Client.builder().id(1L).tier(CustomerTier.SILVER).build();
        Product product = Product.builder().id(10L).price(1000.0).stock(10).build();

        OrderDtoRequest request = OrderDtoRequest.builder()
                .clientId(1L)
                .items(List.of(OrderItemDtoRequest.builder().productId(10L).quantity(1).build()))
                .build();

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(productRepository.findById(10L)).thenReturn(Optional.of(product));

        // Mock Mapper response logic simplified
        when(orderMapper.toDto(any(Order.class))).thenAnswer(i -> {
            Order o = i.getArgument(0);
            return OrderDtoResponse.builder().discountAmount(o.getDiscountAmount()).build();
        });
        when(orderRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        // Act
        OrderDtoResponse response = orderService.createOrder(request);

        // Assert
        // 1000 DH > 500 DH (Silver Threshold) -> 5% Discount
        assertEquals(50.0, response.getDiscountAmount());
    }
}