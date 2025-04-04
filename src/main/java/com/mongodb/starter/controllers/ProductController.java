package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.mongodb.starter.dtos.ProductDTO;
import com.mongodb.starter.entity.ProductEntity;
import com.mongodb.starter.usecases.interfaces.ProductUsecase;

@RestController
@RequestMapping("products")
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductUsecase productUsecase;

    public ProductController(ProductUsecase productUsecase) {
        this.productUsecase = productUsecase;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductEntity> getAllProducts() {
        return productUsecase.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductEntity getProduct(@PathVariable String id) {
        return productUsecase.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductEntity createProduct(@RequestBody ProductDTO productDTO) {
        return productUsecase.createProduct(productDTO.toProductEntity());
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductEntity updateProduct(@RequestBody ProductDTO productDTO) {
        return productUsecase.updateProduct(productDTO.toProductEntity());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String id) {
        productUsecase.deleteProduct(id);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}