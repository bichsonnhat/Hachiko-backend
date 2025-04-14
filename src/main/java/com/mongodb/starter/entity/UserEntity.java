package com.mongodb.starter.entity;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;

public class UserEntity {
    private ObjectId id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
    private String phoneNumber;
    private String gender;
    private String isAdmin;

    public UserEntity() {
    }

    public UserEntity(ObjectId id, String firstName, String lastName, Date birthDate, String email, String phoneNumber, String gender, String isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.isAdmin = isAdmin;
    }

    public ObjectId getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public UserEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserEntity setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserEntity setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UserEntity setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", isAdmin='" + isAdmin + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(id, userEntity.id) && Objects.equals(firstName,userEntity.firstName) 
        && Objects.equals(lastName, userEntity.lastName) && Objects.equals(birthDate, userEntity.birthDate) 
        && Objects.equals(email, userEntity.email) && Objects.equals(phoneNumber, userEntity.phoneNumber) 
        && Objects.equals(gender, userEntity.gender) && Objects.equals(isAdmin, userEntity.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, email, phoneNumber, gender, isAdmin);
    }
}
