package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.repositories.interfaces.UserRepository;
import com.mongodb.starter.usecases.interfaces.UserUsecase;

@Service
public class UserUsecaseImpl implements UserUsecase {
    private final UserRepository userRepository;

    public UserUsecaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(String id) {
        return userRepository.findById(id)
                .map(UserDTO::new);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new);
    }

    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .map(UserDTO::new);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.id() == null || userDTO.id().isEmpty()) {
            throw new IllegalArgumentException("User ID must be provided");
        }
        
        UserEntity userEntity = userDTO.toUserEntity();
        UserEntity savedUser = userRepository.save(userEntity);
        return new UserDTO(savedUser);
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        // Verify the User exists
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + id));
        
        // Ensure the ID in the path is used for the update
        UserEntity userEntity = userDTO.toUserEntity();
        userEntity.setId(id);
        
        UserEntity updatedUser = userRepository.update(userEntity);
        return new UserDTO(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
