package com.mongodb.starter.usecases.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserVoucherDTO;

@Service
public interface UserVoucherUsecase {
    List<UserVoucherDTO> getAllUserVouchers();
    
    Optional<UserVoucherDTO> getUserVoucherById(String id);
    
    List<UserVoucherDTO> getUserVouchersByUserId(String userId);
    
    List<UserVoucherDTO> getUserVouchersByVoucherId(String voucherId);
    
    UserVoucherDTO createUserVoucher(UserVoucherDTO userVoucherDTO);
    
    UserVoucherDTO updateUserVoucher(String id, UserVoucherDTO userVoucherDTO);
    
    void deleteUserVoucher(String id);
} 