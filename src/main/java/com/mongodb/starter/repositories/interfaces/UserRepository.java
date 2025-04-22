package com.mongodb.starter.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.UserEntity;

@Repository
public interface UserRepository {
    List<UserEntity> findAll();
    
    Optional<UserEntity> findById(String id);
    
    Optional<UserEntity> findByEmail(String email);
    
    Optional<UserEntity> findByPhoneNumber(String phoneNumber);
    
    UserEntity save(UserEntity userEntity);
    
    UserEntity update(UserEntity userEntity);
    
    void deleteById(String id);
}
