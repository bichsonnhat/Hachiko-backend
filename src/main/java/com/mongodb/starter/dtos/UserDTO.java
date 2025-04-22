package com.mongodb.starter.dtos;

import com.mongodb.starter.entity.UserEntity;

import java.util.Date;

public record UserDTO(
        String id,
        String firstName,
        String lastName,
        Date birthDate,
        String email,
        String phoneNumber,
        String gender,
        boolean isAdmin) {

    public UserDTO(UserEntity userEntity) {
        this(userEntity.getId(),
             userEntity.getFirstName(),
             userEntity.getLastName(),
             userEntity.getBirthDate(),
             userEntity.getEmail(),
             userEntity.getPhoneNumber(),
             userEntity.getGender(),
             userEntity.isAdmin());
    }

    public UserEntity toUserEntity() {
        return new UserEntity(id, firstName, lastName, birthDate, email, phoneNumber, gender, isAdmin);
    }
}
