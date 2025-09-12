package com.e_commerce.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CartItem {
    
    @Id
    private String productName;
    private int quantity;
    private double price;
}
