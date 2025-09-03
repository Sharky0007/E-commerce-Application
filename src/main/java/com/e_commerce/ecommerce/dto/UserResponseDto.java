package com.e_commerce.ecommerce.dto;

import com.e_commerce.ecommerce.enums.Roles;

import lombok.Data;

@Data
public class UserResponseDto {
    
    private String username;

    private String email;

    private Roles role;
}
