package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.FilteredProductResponse;
import com.mongodb.starter.dtos.ProductDTO;
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
    public ProductDTO createProduct(ProductDTO productEntity) {
        return new ProductDTO(productRepository.insertOne(productEntity.toProductEntity()));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDTO::new).toList();
    }

    @Override
    public ProductDTO getProduct(String id) {
        return new ProductDTO(productRepository.findOne(id));
    }

    @Override
    public ProductDTO updateProduct(ProductDTO entity) {
        return new ProductDTO(productRepository.updateOne(entity.toProductEntity()));
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteOne(id);
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String id) {
        return productRepository.findByCategory(id).stream().map(ProductDTO::new).toList();
    }

    @Override
    public List<Document> getAllGroupedByCategoryAggregation() {
        return productRepository.findAllGroupedByCategoryAggregation();
    }

    @Override
    public FilteredProductResponse<ProductDTO> filterProduct(String search, Integer page) {
        FilteredProductResponse<ProductEntity> response = productRepository.filterProduct(search, page);

        List<ProductDTO> dtoList = response.getItems().stream()
            .map(ProductDTO::new)
            .toList();

    return new FilteredProductResponse<>(dtoList, response.getTotalPages());
}

}