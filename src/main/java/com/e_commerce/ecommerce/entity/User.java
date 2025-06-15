package com.e_commerce.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    
    @GeneratedValue
    @Id
    private long id;

    private String username;

    private String email;

    private String password;
}