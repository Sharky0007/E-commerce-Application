package com.e_commerce.ecommerce.dto;

import java.util.List;

import com.e_commerce.ecommerce.entity.CartItem;

import lombok.Data;

@Data
public class CartResponseDto {

    private String username;
    private double totalPrice;
    private List<CartItem> items;
    
}
