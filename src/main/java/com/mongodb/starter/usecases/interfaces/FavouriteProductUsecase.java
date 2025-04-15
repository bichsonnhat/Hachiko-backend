package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.FavouriteProductDTO;
import com.mongodb.starter.dtos.ProductDTO;
import com.mongodb.starter.entity.FavouriteProductEntity;

@Service
public interface FavouriteProductUsecase {
    List<ProductDTO> getFavouriteProductsByUserId(String userId);

    FavouriteProductDTO addNewProductIntoFavouriteProductList(FavouriteProductEntity favouriteProductEntity);

    void removeProductFromFavouriteProductList(String id);
}
