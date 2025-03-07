package com.mongodb.starter.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.UserDTO;
import com.mongodb.starter.entity.UserEntity;
import com.mongodb.starter.usecases.interfaces.UserUsecase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class User {

    private final static Logger LOGGER = LoggerFactory.getLogger(User.class);
    private final UserUsecase userUsecase;

    public User(UserUsecase userUsecase) {
        this.userUsecase = userUsecase;
    }

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserEntity createUser(@RequestBody UserDTO userDTO) {
        return userUsecase.createUser(userDTO.toUserEntity());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}
