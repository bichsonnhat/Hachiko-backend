package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;

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
        String isAdmin) {

    public UserDTO(UserEntity userEntity) {
        this(userEntity.getId() == null ? new ObjectId().toHexString() : userEntity.getId().toHexString(),
                userEntity.getFirstName(), userEntity.getLastName(), userEntity.getBirthDate(), userEntity.getEmail(),
                userEntity.getPhoneNumber(), userEntity.getGender(), userEntity.getIsAdmin());
    }

    public UserEntity toUserEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new UserEntity(_id, firstName, lastName, birthDate, email, phoneNumber, gender, isAdmin);
    }
}
