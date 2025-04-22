package com.mongodb.starter.dtos;

import java.util.Date;

import com.mongodb.starter.entity.UserVoucher;

public record UserVoucherDTO(
        String id,
        String userId,
        String voucherId,
        Date createdAt,
        Date updatedAt,
        String status) {
    
    public UserVoucherDTO(UserVoucher userVoucher) {
        this(
            userVoucher.getId() == null ? null : userVoucher.getId().toHexString(),
            userVoucher.getUserId() == null ? null : userVoucher.getUserId().toHexString(),
            userVoucher.getVoucherId() == null ? null : userVoucher.getVoucherId().toHexString(),
            userVoucher.getCreatedAt(),
            userVoucher.getUpdatedAt(),
            userVoucher.getStatus()
        );
    }

    public UserVoucher toUserVoucher() {
        return new UserVoucher(
            id == null ? null : new org.bson.types.ObjectId(id),
            userId == null ? null : new org.bson.types.ObjectId(userId),
            voucherId == null ? null : new org.bson.types.ObjectId(voucherId),
            createdAt,
            updatedAt,
            status
        );
    }
} 