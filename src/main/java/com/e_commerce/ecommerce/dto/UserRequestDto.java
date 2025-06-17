package com.e_commerce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDto {
    
    @NotBlank(message = "Username can't be null or empty")
    private String username;
    
    private String email;

    private String password;
}
