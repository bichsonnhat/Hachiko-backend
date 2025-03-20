package com.mongodb.starter.repositories.interfaces;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.UserEntity;


@Repository
public interface UserRepository {
    UserEntity insertOne(UserEntity userEntity);
}
