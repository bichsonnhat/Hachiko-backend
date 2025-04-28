package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import org.bson.Document;

import com.mongodb.starter.dtos.FilteredProductResponse;
import com.mongodb.starter.dtos.ProductDTO;

public interface ProductUsecase  {
    ProductDTO createProduct(ProductDTO productEntity);
    
    List<ProductDTO> getAllProducts();
    
    ProductDTO getProduct(String id);
    
    ProductDTO updateProduct(ProductDTO entity);

    void deleteProduct(String id);

    List<ProductDTO> getProductsByCategory(String id);

    List<Document> getAllGroupedByCategoryAggregation();

    FilteredProductResponse<ProductDTO> filterProduct(String search, Integer page);
}
