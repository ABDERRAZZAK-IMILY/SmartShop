package com.microtech.smartshop.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDtoRequest {

    @NotBlank(message = "full name is required")
    private String fullName;

    @Email(message = "email is required")
    private String email;

    @NotBlank(message = "username is required")
    private String Username;


    @NotNull(message = "password is required")
    private String Password;




}
