package com.mongodb.starter.entity;

import java.util.Date;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoucherEntity {
    private ObjectId id;
    private ObjectId userId;
    private String title;
    private String description;
    private String imgUrl;
    private int discountPrice;
    private double discountPercent;
    private boolean isFreeShip;
    private int minOrderPrice;
    private int minOrderItem;
    private String type;
    private Date expiryDate;
}
