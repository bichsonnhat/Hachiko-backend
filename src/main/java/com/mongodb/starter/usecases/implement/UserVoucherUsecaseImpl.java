package com.mongodb.starter.usecases.implement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserVoucherDTO;
import com.mongodb.starter.dtos.VoucherDTO;
import com.mongodb.starter.entity.UserVoucher;
import com.mongodb.starter.repositories.interfaces.UserVoucherRepository;
import com.mongodb.starter.usecases.interfaces.UserVoucherUsecase;
import com.mongodb.starter.usecases.interfaces.VoucherUsecase;

@Service
public class UserVoucherUsecaseImpl implements UserVoucherUsecase {
    private final UserVoucherRepository userVoucherRepository;
    private final VoucherUsecase voucherUsecase;

    public UserVoucherUsecaseImpl(UserVoucherRepository userVoucherRepository, VoucherUsecase voucherUsecase) {
        this.userVoucherRepository = userVoucherRepository;
        this.voucherUsecase = voucherUsecase;
    }

    @Override
    public List<UserVoucherDTO> getAllUserVouchers() {
        return userVoucherRepository.findAll().stream()
                .map(UserVoucherDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserVoucherDTO> getUserVoucherById(String id) {
        return userVoucherRepository.findById(id)
                .map(UserVoucherDTO::new);
    }

    @Override
    public List<UserVoucherDTO> getUserVouchersByUserId(String userId) {
        return userVoucherRepository.findByUserId(userId).stream()
                .map(UserVoucherDTO::new)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UserVoucherDTO> getAvailableVouchersByUserId(String userId) {
        // Get active user vouchers
        List<UserVoucher> activeUserVouchers = userVoucherRepository.findAvailableByUserId(userId);
        List<UserVoucherDTO> availableVouchers = new ArrayList<>();
        Date currentDate = new Date();
        
        // Check each voucher's expiry date
        for (UserVoucher userVoucher : activeUserVouchers) {
            String voucherId = userVoucher.getVoucherId().toHexString();
            Optional<VoucherDTO> voucherOpt = voucherUsecase.getVoucherById(voucherId);
            
            if (voucherOpt.isPresent()) {
                VoucherDTO voucher = voucherOpt.get();
                // Only include if not expired
                if (voucher.expiryDate() == null || voucher.expiryDate().after(currentDate)) {
                    availableVouchers.add(new UserVoucherDTO(userVoucher));
                }
            }
        }
        
        return availableVouchers;
    }

    @Override
    public List<UserVoucherDTO> getUserVouchersByVoucherId(String voucherId) {
        return userVoucherRepository.findByVoucherId(voucherId).stream()
                .map(UserVoucherDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserVoucherDTO createUserVoucher(UserVoucherDTO userVoucherDTO) {
        UserVoucher userVoucher = userVoucherDTO.toUserVoucher();
        userVoucher.setId(null); // Ensure we're creating a new document
        UserVoucher savedUserVoucher = userVoucherRepository.save(userVoucher);
        return new UserVoucherDTO(savedUserVoucher);
    }

    @Override
    public UserVoucherDTO updateUserVoucher(String id, UserVoucherDTO userVoucherDTO) {
        // Verify the UserVoucher exists
        userVoucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserVoucher not found with id: " + id));
        
        UserVoucher userVoucher = userVoucherDTO.toUserVoucher();
        userVoucher.setId(new ObjectId(id));
        
        UserVoucher updatedUserVoucher = userVoucherRepository.update(userVoucher);
        return new UserVoucherDTO(updatedUserVoucher);
    }

    @Override
    public void deleteUserVoucher(String id) {
        userVoucherRepository.deleteById(id);
    }
} 