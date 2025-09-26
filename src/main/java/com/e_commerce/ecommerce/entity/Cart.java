package com.e_commerce.ecommerce.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Cart {

    @OneToMany
    private List<CartItem> items;

    private double totalPrice;

    @Id
    private String userName;
}
