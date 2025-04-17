package com.mongodb.starter.dtos;

import java.util.List;

public record FavouriteProductsResponse(
        List<FavouriteProductDTO> favouriteProducts,
        List<ProductDTO> products) {
    public FavouriteProductsResponse(List<FavouriteProductDTO> favouriteProducts, List<ProductDTO> products) {
        this.favouriteProducts = favouriteProducts;
        this.products = products;
    }
}
