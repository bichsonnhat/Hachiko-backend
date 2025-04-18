package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.VoucherEntity;

@Repository
public interface VoucherRepository {
    List<VoucherEntity> findAll();

    List<VoucherEntity> findByUserId(String userId);
}
