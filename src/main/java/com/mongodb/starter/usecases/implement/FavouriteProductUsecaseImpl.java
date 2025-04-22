package com.mongodb.starter.usecases.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.FavouriteProductDTO;
import com.mongodb.starter.dtos.FavouriteProductsResponse;
import com.mongodb.starter.dtos.ProductDTO;
import com.mongodb.starter.entity.FavouriteProductEntity;
import com.mongodb.starter.entity.ProductEntity;
import com.mongodb.starter.repositories.interfaces.FavouriteProductRepository;
import com.mongodb.starter.repositories.interfaces.ProductRepository;
import com.mongodb.starter.usecases.interfaces.FavouriteProductUsecase;

@Service
public class FavouriteProductUsecaseImpl implements FavouriteProductUsecase {
    private final FavouriteProductRepository favouriteProductRepository;
    private final ProductRepository productRepository;

    public FavouriteProductUsecaseImpl(FavouriteProductRepository favouriteProductRepository,
            ProductRepository productRepository) {
        this.favouriteProductRepository = favouriteProductRepository;
        this.productRepository = productRepository;
    }

    @Override
    public FavouriteProductDTO addNewProductIntoFavouriteProductList(FavouriteProductEntity favouriteProductEntity) {
        FavouriteProductEntity savedEntity = favouriteProductRepository
                .addNewProductIntoFavouriteProductList(favouriteProductEntity);
        return new FavouriteProductDTO(savedEntity);
    }

    @Override
    public FavouriteProductsResponse getFavouriteProductsByUserId(String userId) {
        List<FavouriteProductEntity> entities = favouriteProductRepository.getFavouriteProductsByUserId(userId);
        List<FavouriteProductDTO> fav = entities.stream()
                .map(entity -> new FavouriteProductDTO(entity))
                .collect(Collectors.toList());
        List<String> productIds = entities.stream()
                .map(favouriteProduct -> favouriteProduct.getProductId().toHexString())
                .collect(Collectors.toList());

        List<ProductEntity> productsInList = productRepository.findAllByProductIds(productIds);
        List<ProductDTO> productDTOs = productsInList.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        FavouriteProductsResponse favouriteProductsResponse = new FavouriteProductsResponse(fav, productDTOs);
        return favouriteProductsResponse;
    }

    @Override
    public void removeProductFromFavouriteProductList(String id) {
        favouriteProductRepository.removeProductFromFavouriteProductList(id);
    }

    @Override
    public boolean checkIfProductIsInFavouriteProductList(String userId, String productId) {
        return favouriteProductRepository.checkIfProductIsInFavouriteProductList(userId, productId);
    }
}
