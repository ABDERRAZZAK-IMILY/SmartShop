package com.microtech.smartshop.dto;


import com.microtech.smartshop.enums.Role;
import com.microtech.smartshop.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoResponse {

    private Long id;

    private String username;

    private Role role;

    private Client client;


}
