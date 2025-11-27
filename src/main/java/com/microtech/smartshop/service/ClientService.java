package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.ClientDtoRequest;
import com.microtech.smartshop.dto.ClientDtoResponse;

import java.util.List;

public interface ClientService {


    public List<ClientDtoResponse> getAllClients();
    public ClientDtoResponse createClient(ClientDtoRequest request);

    public ClientDtoResponse getClientById(Long id);
    ClientDtoResponse updateClient(Long id, ClientDtoRequest request);
    public void deleteClient(Long id);
}
