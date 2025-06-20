package com.e_commerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.ecommerce.dto.AuthReqBody;
import com.e_commerce.ecommerce.dto.AuthResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthReqBody reqBody){
        AuthResponse authResponse = new AuthResponse();
        return ResponseEntity.ok().body(authResponse);
    }
}
