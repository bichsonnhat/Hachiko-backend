package com.mongodb.starter.usecases.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserDTO;

@Service
public interface UserUsecase {
    List<UserDTO> getAllUsers();
    
    Optional<UserDTO> getUserById(String id);
    
    Optional<UserDTO> getUserByEmail(String email);
    
    Optional<UserDTO> getUserByPhoneNumber(String phoneNumber);
    
    UserDTO createUser(UserDTO userDTO);
    
    UserDTO updateUser(String id, UserDTO userDTO);
    
    void deleteUser(String id);
}
