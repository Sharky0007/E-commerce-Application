package com.e_commerce.ecommerce.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.e_commerce.ecommerce.dto.UserRequestDto;
import com.e_commerce.ecommerce.dto.UserResponseDto;
import com.e_commerce.ecommerce.exceptions.DataNotFoundException;
import com.e_commerce.ecommerce.exceptions.IdExistException;
import com.e_commerce.ecommerce.service.UserRegistrationsvc;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationsvc userRegistrationsvc;

    @PostMapping(value = "/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto user) throws IdExistException{
        
        UserResponseDto dto = userRegistrationsvc.addUser(user); 

        URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/get/{username}")
        .buildAndExpand(user.getUsername())
        .toUri();

        return ResponseEntity.created(location).body(dto);

    } 

    @GetMapping("/get/{username}")
    public ResponseEntity<UserResponseDto> getUserByName(@PathVariable String username) throws DataNotFoundException{
        
        UserResponseDto res = userRegistrationsvc.getUserByName(username);

        return ResponseEntity.ok().body(res);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String username,@RequestBody UserRequestDto userreq) throws DataNotFoundException{

        UserResponseDto userres = userRegistrationsvc.updateUser(username, userreq);

        return ResponseEntity.ok().body(userres);
    }

    @GetMapping("/get/getalluser")
    public ResponseEntity<List<UserResponseDto>> getAllUser(){

        List<UserResponseDto> allUser = userRegistrationsvc.getAllUser();
        
        return ResponseEntity.ok().body(allUser);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) throws DataNotFoundException{

        userRegistrationsvc.deleteUser(username);

        return ResponseEntity.ok().body("User " + username + " deleted successfully");
    }
    
}
