package com.microtech.smartshop.mapper;


import com.microtech.smartshop.dto.ClientDtoResponse;
import com.microtech.smartshop.dto.UserDtoRequest;
import com.microtech.smartshop.dto.UserDtoResponse;
import com.microtech.smartshop.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private ClientDtoResponse clientDto;

    public User toEntity(UserDtoRequest dto) {

        return  User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

    }

    public UserDtoResponse toDto(User user){

        return UserDtoResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .client(clientDto)
                .build();


    }



}
