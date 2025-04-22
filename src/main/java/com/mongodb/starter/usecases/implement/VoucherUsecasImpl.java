package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.VoucherDTO;
import com.mongodb.starter.repositories.interfaces.VoucherRepository;
import com.mongodb.starter.usecases.interfaces.VoucherUsecase;

@Service
public class VoucherUsecasImpl implements VoucherUsecase {
    private final VoucherRepository voucherRepository;

    public VoucherUsecasImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public List<VoucherDTO> getAllVouchers() {
        List<VoucherDTO> vouchers = voucherRepository.findAll().stream().map(VoucherDTO::new).toList();
        return vouchers;
    }

    @Override
    public List<VoucherDTO> getVouchersByUserId(String userId) {
        List<VoucherDTO> vouchers = voucherRepository.findByUserId(userId).stream().map(VoucherDTO::new).toList();
        return vouchers;
    }

}
