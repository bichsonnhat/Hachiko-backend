package com.mongodb.starter.dtos;

import java.util.Date;

import com.mongodb.starter.entity.VoucherEntity;

public record VoucherDTO(
        String id,
        String title,
        String description,
        String imgUrl,
        int discountPrice,
        double discountPercent,
        boolean isFreeShip,
        int minOrderPrice,
        int minOrderItem,
        String type,
        Date expiryDate) {
    public VoucherDTO(VoucherEntity voucherEntity) {
        this(voucherEntity.getId() == null ? null : voucherEntity.getId().toHexString(),
                voucherEntity.getTitle(), voucherEntity.getDescription(), voucherEntity.getImgUrl(),
                voucherEntity.getDiscountPrice(), voucherEntity.getDiscountPercent(),
                voucherEntity.isFreeShip(), voucherEntity.getMinOrderPrice(),
                voucherEntity.getMinOrderItem(), voucherEntity.getType(), voucherEntity.getExpiryDate());
    }

    public VoucherEntity toVoucherEntity() {
        return new VoucherEntity(id == null ? null : new org.bson.types.ObjectId(id),
                title, description, imgUrl, discountPrice, discountPercent, 
                isFreeShip, minOrderPrice, minOrderItem, type, expiryDate);
    }
}
