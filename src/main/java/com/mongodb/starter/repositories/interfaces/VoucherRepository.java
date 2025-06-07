package com.mongodb.starter.repositories.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.VoucherEntity;

@Repository
public interface VoucherRepository {
    List<VoucherEntity> findAll();
    
    Optional<VoucherEntity> findById(String id);
    
    List<VoucherEntity> findByIds(List<String> ids);
    
    VoucherEntity save(VoucherEntity voucherEntity);
    
    VoucherEntity update(VoucherEntity voucherEntity);
    
    void deleteById(String id);
}
