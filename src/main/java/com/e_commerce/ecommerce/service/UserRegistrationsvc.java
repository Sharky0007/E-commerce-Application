package com.e_commerce.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.e_commerce.ecommerce.dto.UserRequestDto;
import com.e_commerce.ecommerce.dto.UserResponseDto;
import com.e_commerce.ecommerce.entity.User;
import com.e_commerce.ecommerce.enums.Roles;
import com.e_commerce.ecommerce.exceptions.DataNotFoundException;
import com.e_commerce.ecommerce.exceptions.IdExistException;
import com.e_commerce.ecommerce.repo.UserRepo;

@Service
public class UserRegistrationsvc {

    private PasswordEncoder passwordEncoder;

    private final UserRepo userRepo;
    
    public UserRegistrationsvc(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDto addUser(UserRequestDto userreq) throws IdExistException{

            User user = userRepo.findByUsername(userreq.getUsername());
            User newUser = new User();
            if(user == null){
                String hashedPassword = passwordEncoder.encode(userreq.getPassword());
                newUser.setUsername(userreq.getUsername());
                newUser.setPassword(hashedPassword);
                newUser.setEmail(userreq.getEmail());
                if(userreq.getRole() == null){
                    newUser.setRole(Roles.USER);
                }
                else{
                     newUser.setRole(userreq.getRole());
                }
                userRepo.save(newUser);
            }
            else{
                throw new IdExistException("User " + userreq.getUsername() + " already exist.");
            }
            UserResponseDto dto = userToDto(newUser);

            return dto;
    }

    public UserResponseDto getUserByName(String username) throws DataNotFoundException{

        UserResponseDto res = new UserResponseDto();
        User user = userRepo.findByUsername(username);

        if(user != null){
            res.setEmail(user.getEmail());
            res.setUsername(user.getUsername());
            res.setRole(user.getRole());
        }
        else{
            throw new DataNotFoundException("Data not found for: " + username);
        }
        return res;
    }

    public UserResponseDto updateUser(String username, UserRequestDto userreq) throws DataNotFoundException {
        UserResponseDto userres = new UserResponseDto();

        User user = userRepo.findByUsername(username);

        if(user != null){
            if(userreq.getEmail() != null){
                user.setEmail(userreq.getEmail());
            }
            if(userreq.getPassword()!=null){
                user.setPassword(userreq.getPassword());  
            }  
            if(userreq.getRole()!=null){
                user.setRole(userreq.getRole());
            } 
                userRepo.save(user);
                BeanUtils.copyProperties(user, userres);
        }
        else{
            throw new DataNotFoundException("User " + username + " not found for update, please try to update valid user");
        }

        return userres;
        
     }

    public List<UserResponseDto> getAllUser() {
        List<User> users = userRepo.findAll();
        List<UserResponseDto> userres = new ArrayList<>();

        for(User u: users){
            UserResponseDto res = new UserResponseDto();
            BeanUtils.copyProperties(u, res);
            userres.add(res);

        }
        return userres;
    }

    public void deleteUser(String username) throws DataNotFoundException{

        User res = userRepo.findByUsername(username);
        if(res != null){
            userRepo.delete(res);
        }
        else{
            throw new DataNotFoundException("No data found for " + username);
        }
    }

    public UserResponseDto userToDto(User user){
        UserResponseDto dto = new UserResponseDto();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }
}
