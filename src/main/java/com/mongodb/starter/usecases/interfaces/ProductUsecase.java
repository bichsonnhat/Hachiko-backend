package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import com.mongodb.starter.entity.ProductEntity;

public interface ProductUsecase  {
    ProductEntity createProduct(ProductEntity productEntity);
    
    List<ProductEntity> getAllProducts();
    
    ProductEntity getProduct(String id);
    
    ProductEntity updateProduct(ProductEntity entity);

    void deleteProduct(String id);
}
