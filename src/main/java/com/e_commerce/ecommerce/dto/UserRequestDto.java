package com.e_commerce.ecommerce.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    
    private String username;
    
    private String email;

    private String password;
}
