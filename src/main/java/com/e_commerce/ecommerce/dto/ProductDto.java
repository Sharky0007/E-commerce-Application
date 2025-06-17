package com.e_commerce.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDto {
    
    @NotBlank(message = "ProductName can't be null or empty")
    private String productName;

    private String description;

    private String category;

    private double price;
}
