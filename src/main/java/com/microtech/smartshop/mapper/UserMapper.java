package com.microtech.smartshop.mapper;


import com.microtech.smartshop.dto.UserDtoResponse;
import com.microtech.smartshop.dto.UserDtorequest;
import com.microtech.smartshop.model.User;

public class UserMapper {


    public User toEntity(UserDtorequest dto) {

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
                .client(user.getClient())
                .build();


    }



}
