package com.microtech.smartshop.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDtoRequest {

    private String fullName;
    private String email;

    private String Username;

    private String Password;




}
