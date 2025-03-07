package com.mongodb.starter.usecases.interfaces;

import com.mongodb.starter.entity.UserEntity;

public interface UserUsecase {
    UserEntity createUser(UserEntity userEntity);
}
