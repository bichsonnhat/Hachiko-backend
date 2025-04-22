package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.UserVoucherDTO;
import com.mongodb.starter.entity.UserVoucher;
import com.mongodb.starter.repositories.interfaces.UserVoucherRepository;
import com.mongodb.starter.usecases.interfaces.UserVoucherUsecase;

@Service
public class UserVoucherUsecaseImpl implements UserVoucherUsecase {
    private final UserVoucherRepository userVoucherRepository;

    public UserVoucherUsecaseImpl(UserVoucherRepository userVoucherRepository) {
        this.userVoucherRepository = userVoucherRepository;
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