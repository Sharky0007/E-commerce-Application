package com.e_commerce.ecommerce.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.e_commerce.ecommerce.dto.UserResponseDto;
import com.e_commerce.ecommerce.exceptions.DataNotFoundException;
import com.e_commerce.ecommerce.service.UserRegistrationsvc;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

public class JwtUtils {
    @Autowired
    private UserRegistrationsvc registrationsvc;

    private final Key SECRET_KEY = Keys.hmacShaKeyFor("l86BMjDJffdphYPZwWgb1XIoS/+eAyW0dRWgOxr8B88=".getBytes());

    public String generateToken(String username) throws InvalidKeyException, DataNotFoundException {
        return Jwts.builder()
                .setClaims(setCustomClaims(registrationsvc.getUserByName(username)))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody().getSubject();
   }

   public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
   } 

    private Map<String, Object> setCustomClaims(UserResponseDto user){
        Map<String, Object> claims = new HashMap<>();

        claims.put("ROLE", user.getRole());
        claims.put("USER", user.getUsername());
        return claims;
    }
}
