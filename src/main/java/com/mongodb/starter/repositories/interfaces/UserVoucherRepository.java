package com.mongodb.starter.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.UserVoucher;

@Repository
public interface UserVoucherRepository {
    List<UserVoucher> findAll();
    
    Optional<UserVoucher> findById(String id);
    
    List<UserVoucher> findByUserId(String userId);
    
    List<UserVoucher> findByVoucherId(String voucherId);
    
    UserVoucher save(UserVoucher userVoucher);
    
    UserVoucher update(UserVoucher userVoucher);
    
    void deleteById(String id);
} 