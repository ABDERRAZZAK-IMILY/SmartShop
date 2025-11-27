package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.ClientDtoResponse;
import com.microtech.smartshop.dto.ClientDtoRequest;
import com.microtech.smartshop.enums.Role;
import com.microtech.smartshop.exception.BusinessException;
import com.microtech.smartshop.exception.ResourceNotFoundException;
import com.microtech.smartshop.mapper.ClientMapper;
import com.microtech.smartshop.model.Client;
import com.microtech.smartshop.model.User;
import com.microtech.smartshop.repository.ClientRepository;
import com.microtech.smartshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ClientDtoResponse createClient(ClientDtoRequest request) {

        if (clientRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Client with this email already exists.");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BusinessException("Username already exists.");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(Role.CLIENT)
                .build();

        User savedUser = userRepository.save(user);

        Client client = clientMapper.toEntity(request);
        client.setUser(savedUser);

        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public List<ClientDtoResponse> getAllClients() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDtoResponse getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
        return clientMapper.toDto(client);
    }

    @Override
    public ClientDtoResponse updateClient(Long id, ClientDtoRequest request) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        if (!client.getEmail().equals(request.getEmail()) && clientRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already in use by another client.");
        }

        client.setFullName(request.getFullName());
        client.setEmail(request.getEmail());

        return clientMapper.toDto(clientRepository.save(client));
    }

    @Override
    public  void deleteClient(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));

        clientRepository.delete(client);
    }

}