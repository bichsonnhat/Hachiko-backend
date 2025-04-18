package com.mongodb.starter.usecases.interfaces;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.FavouriteProductDTO;
import com.mongodb.starter.dtos.FavouriteProductsResponse;
import com.mongodb.starter.entity.FavouriteProductEntity;

@Service
public interface FavouriteProductUsecase {
    FavouriteProductsResponse getFavouriteProductsByUserId(String userId);

    FavouriteProductDTO addNewProductIntoFavouriteProductList(FavouriteProductEntity favouriteProductEntity);

    void removeProductFromFavouriteProductList(String id);

    boolean checkIfProductIsInFavouriteProductList(String userId, String productId);
}
