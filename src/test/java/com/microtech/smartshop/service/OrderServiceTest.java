package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.OrderDtoResponse;
import com.microtech.smartshop.mapper.OrderMapper;
import com.microtech.smartshop.model.Order;
import com.microtech.smartshop.repository.ClientRepository;
import com.microtech.smartshop.repository.OrderRepository;
import com.microtech.smartshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private ProductRepository productRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private OrderMapper orderMapper;

    @InjectMocks private OrderServiceImpl orderService;

    @Test
    void  shouldgetOrderById(){

        Long id = 1L;

        Order order = Order.builder().id(1L).build();

        Order findedorder = Order.builder().id(1L).build();

        when(orderRepository.findById(id)).thenReturn(Optional.of(findedorder));

        OrderDtoResponse orderresponse = OrderDtoResponse.builder().id(1L).build();

        when(orderMapper.toDto(findedorder)).thenReturn(orderresponse);


        // Act


        OrderDtoResponse response = orderService.getOrderById(1L);


        assertEquals(response , orderresponse);

        verify(orderMapper).toDto(findedorder);




    }

}