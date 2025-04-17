package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.FavouriteProductEntity;

@Repository
public interface FavouriteProductRepository {
    List<FavouriteProductEntity> getFavouriteProductsByUserId(String userId);

    FavouriteProductEntity addNewProductIntoFavouriteProductList(FavouriteProductEntity favouriteProductEntity);

    void removeProductFromFavouriteProductList(String id);

    boolean checkIfProductIsInFavouriteProductList(String userId, String productId);
}
