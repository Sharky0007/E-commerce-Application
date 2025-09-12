package com.e_commerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private CartService svc;

    public CartController(CartService svc){
        this.svc = svc;
    }
    
    @PostMapping("/addToCart/{userName}/product/{productName}")
    public ResponseEntity<String> addToCart(@PathVariable String userName, @PathVariable String productName){

        String msg = svc.addToCart(userName, productName);
        return ResponseEntity.ok().body(msg);
    }
}
