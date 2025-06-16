package com.e_commerce.ecommerce.dto;

import lombok.Data;

@Data
public class ProductDto {
    
    private String productName;

    private String description;

    private String category;

    private double price;
}
