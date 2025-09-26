package com.e_commerce.ecommerce.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.ecommerce.entity.CartItem;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long>{

    CartItem findByProductName(String productName);
    
}
