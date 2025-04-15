package com.mongodb.starter.entity;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavouriteProductEntity {
    private ObjectId id;
    private ObjectId userId;
    private ObjectId productId;
}
