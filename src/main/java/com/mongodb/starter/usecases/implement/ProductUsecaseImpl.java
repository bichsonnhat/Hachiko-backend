package com.mongodb.starter.usecases.implement;

import java.util.List;
import org.springframework.stereotype.Service;

import com.mongodb.starter.entity.ProductEntity;
import com.mongodb.starter.repositories.interfaces.ProductRepository;
import com.mongodb.starter.usecases.interfaces.ProductUsecase;

@Service
public class ProductUsecaseImpl implements ProductUsecase {

    private final ProductRepository productRepository;

    public ProductUsecaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity createProduct(ProductEntity productEntity) {
        return productRepository.insertOne(productEntity);
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getProduct(String id) {
        return productRepository.findOne(id);
    }

    @Override
    public ProductEntity updateProduct(ProductEntity entity) {
        return productRepository.updateOne(entity);
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteOne(id);
    }

    @Override
    public List<ProductEntity> getProductsByCategory(String id) {
        return productRepository.findByCategory(id);
    }
}