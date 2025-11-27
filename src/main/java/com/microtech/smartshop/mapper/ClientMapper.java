package com.microtech.smartshop.mapper;

import com.microtech.smartshop.dto.ClientDtoResponse;
import com.microtech.smartshop.dto.ClientDtoRequest;
import com.microtech.smartshop.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client toEntity(ClientDtoRequest dto) {
        if (dto == null) return null;
        return Client.builder()
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .build();
    }

    public ClientDtoResponse toDto(Client client) {
        if (client == null) return null;
        return ClientDtoResponse.builder()
                .id(client.getId())
                .fullName(client.getFullName())
                .email(client.getEmail())
                .tier(client.getTier())
                .totalSpent(client.getTotalSpent())
                .totalOrders(client.getTotalOrders())
                .build();
    }
}