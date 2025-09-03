package com.e_commerce.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Product {
    
    @Id
    @NotBlank(message = "ProductName can't be blank")
    private String productName;

    private String description;

    private String category;

    private double price;

    private int productCount;
}
