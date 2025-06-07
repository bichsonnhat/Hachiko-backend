package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.VoucherDTO;
import com.mongodb.starter.entity.VoucherEntity;
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
    public List<VoucherDTO> getVouchersByIds(List<String> ids) {
        return voucherRepository.findByIds(ids).stream()
                .map(VoucherDTO::new)
                .toList();
    }
    
    @Override
    public Optional<VoucherDTO> getVoucherById(String id) {
        return voucherRepository.findById(id)
                .map(VoucherDTO::new);
    }
    
    @Override
    public VoucherDTO createVoucher(VoucherDTO voucherDTO) {
        VoucherEntity voucherEntity = voucherDTO.toVoucherEntity();
        voucherEntity.setId(null); // Ensure we're creating a new document
        VoucherEntity savedVoucher = voucherRepository.save(voucherEntity);
        return new VoucherDTO(savedVoucher);
    }
    
    @Override
    public VoucherDTO updateVoucher(String id, VoucherDTO voucherDTO) {
        // Verify the Voucher exists
        voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found with id: " + id));
        
        VoucherEntity voucherEntity = voucherDTO.toVoucherEntity();
        voucherEntity.setId(new ObjectId(id));
        
        VoucherEntity updatedVoucher = voucherRepository.update(voucherEntity);
        return new VoucherDTO(updatedVoucher);
    }
    
    @Override
    public void deleteVoucher(String id) {
        voucherRepository.deleteById(id);
    }
}
