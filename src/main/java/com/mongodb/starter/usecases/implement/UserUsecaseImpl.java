package com.mongodb.starter.usecases.implement;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.repositories.interfaces.UserRepository;
import com.mongodb.starter.usecases.interfaces.UserUsecase;

import org.springframework.stereotype.Service;


@Service
public class UserUsecaseImpl implements UserUsecase {

    private final UserRepository userRepository;

    public UserUsecaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return userRepository.createUser(userEntity);
    }
}
