package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.VoucherDTO;

@Service
public interface VoucherUsecase {
    List<VoucherDTO> getAllVouchers();

    List<VoucherDTO> getVouchersByUserId(String userId);
}
