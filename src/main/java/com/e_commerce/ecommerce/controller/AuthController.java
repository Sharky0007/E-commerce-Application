package com.e_commerce.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.e_commerce.ecommerce.dto.AuthReqBody;
import com.e_commerce.ecommerce.dto.AuthResponse;
import com.e_commerce.ecommerce.exceptions.DataNotFoundException;
import com.e_commerce.ecommerce.security.JwtUtils;

import io.jsonwebtoken.security.InvalidKeyException;

@RestController
@RequestMapping("/auth")
public class AuthController {

     private final AuthenticationManager authManager;
    private final JwtUtils jwtUtil;

    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtil) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthReqBody reqBody) throws InvalidKeyException, DataNotFoundException{
        AuthResponse authResponse = new AuthResponse();
        Authentication auth = new UsernamePasswordAuthenticationToken(reqBody.getUsername(), reqBody.getPassword());
        authManager.authenticate(auth);
        String token = jwtUtil.generateToken(reqBody.getUsername());
        authResponse.setToken(token);
        return ResponseEntity.ok().body(authResponse);
    }
}
