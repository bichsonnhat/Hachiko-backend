package com.mongodb.starter.usecases.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.VoucherDTO;

@Service
public interface VoucherUsecase {
    List<VoucherDTO> getAllVouchers();
    
    List<VoucherDTO> getVouchersByIds(List<String> ids);
    
    Optional<VoucherDTO> getVoucherById(String id);
    
    VoucherDTO createVoucher(VoucherDTO voucherDTO);
    
    VoucherDTO updateVoucher(String id, VoucherDTO voucherDTO);
    
    void deleteVoucher(String id);
}
