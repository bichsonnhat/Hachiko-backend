package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.starter.dtos.FilteredProductResponse;
import com.mongodb.starter.entity.ProductEntity;

@Repository
public interface ProductRepository {

    ProductEntity insertOne(ProductEntity productEntity);

    void deleteOne(String id);

    List<ProductEntity> findAll();

    ProductEntity findOne(String id);

    ProductEntity updateOne(ProductEntity entity);

    List<ProductEntity> findByCategory(String id);

    List<Document> findAllGroupedByCategoryAggregation();

    List<ProductEntity> findAllByProductIds(List<String> productIds);

    FilteredProductResponse<ProductEntity> filterProduct(String search, Integer page);
}