package com.e_commerce.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.e_commerce.ecommerce.dto.CustomUserDetails;
import com.e_commerce.ecommerce.entity.User;
import com.e_commerce.ecommerce.repo.UserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User usernameFromDb = userRepo.findByUsername(username);
        if (usernameFromDb!= null) {
            return new CustomUserDetails(usernameFromDb);
        }
        else{
           throw new UsernameNotFoundException("User not found");
        }
    }
}

