package com.microtech.smartshop.service;


import com.microtech.smartshop.dto.ClientDtoRequest;
import com.microtech.smartshop.dto.ClientDtoResponse;
import com.microtech.smartshop.mapper.ClientMapper;
import com.microtech.smartshop.model.Client;
import com.microtech.smartshop.model.User;
import com.microtech.smartshop.repository.ClientRepository;
import com.microtech.smartshop.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.microtech.smartshop.enums.Role.CLIENT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientSeerviceTest {

    @Mock
    private  ClientRepository clientRepository;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;



    @InjectMocks
    private  ClientServiceImpl clientService;


    @Test
    public void isgetClientByid(){

        Long id = 1L;

        ClientDtoResponse response = ClientDtoResponse.builder().id(1L).fullName("ALI").email("azeimily@gmail.com").build();

        Client Entity = Client.builder().id(1L).fullName("Ali").email("azeimily@gmail.com").build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(Entity));

        when(clientMapper.toDto(Entity)).thenReturn(response);

        ClientDtoResponse result = clientService.getClientById(id);

        assertEquals(result , response);

        assertNotNull(result);


    }

    @Test

    public  void  isClientdelete(){

        Long id = 1L;

        Client Entity = Client.builder().id(1L).fullName("Ali").email("azeimily@gmail.com").build();


        when(clientRepository.findById(id)).thenReturn(Optional.of(Entity));

        clientService.deleteClient(id);

        verify(clientRepository).findById(id);
        verify(clientRepository).delete(Entity);


    }


    @Test
    public void getAllClient(){

        Client client1 = Client.builder().id(1L).build();
        Client client2 = Client.builder().id(2L).build();



        ClientDtoResponse clientres1 = ClientDtoResponse.builder().id(1L).build();

        ClientDtoResponse clientres2 = ClientDtoResponse.builder().id(2L).build();

        List<ClientDtoResponse> responsedto = new ArrayList<>();

        responsedto.add(clientres1);
        responsedto.add(clientres2);

        List<Client> reponse = new ArrayList<>();
        reponse.add(client1);
        reponse.add(client2);

        when(clientRepository.findAll()).thenReturn(reponse);

        when(clientMapper.toDto(client1)).thenReturn(clientres1);
        when(clientMapper.toDto(client2)).thenReturn(clientres2);


        List<ClientDtoResponse> result = clientService.getAllClients();

        assertEquals(result , responsedto);



    }



    @Test
    public void isClientCreated(){

        ClientDtoRequest request = ClientDtoRequest.builder().fullName("ali").build();

        ClientDtoResponse response = ClientDtoResponse.builder().id(1L).fullName("ali").build();

        Client Entity = Client.builder().id(1L).fullName("ali").build();


        User user = User.builder().username("ali").password(passwordEncoder.encode("123")).role(CLIENT).build();



        when(userRepository.save(any(User.class))).thenReturn(user);
        when(clientRepository.save(Entity)).thenReturn(Entity);
        when(clientMapper.toDto(Entity)).thenReturn(response);
        when(clientMapper.toEntity(request)).thenReturn(Entity);

        ClientDtoResponse result = clientService.createClient(request);



        assertEquals(response , result);






    }


}
