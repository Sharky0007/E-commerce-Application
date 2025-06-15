package com.e_commerce.ecommerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.ecommerce.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

    User findByUsername(String username);

}
