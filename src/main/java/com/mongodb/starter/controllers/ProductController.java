package com.mongodb.starter.controllers;

import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.mongodb.starter.dtos.ProductDTO;
import com.mongodb.starter.usecases.interfaces.ProductUsecase;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
@RestController 
@RequestMapping("/products")
@Tag(name = "products")
@SecurityScheme(
    name = "api_key",
    type = SecuritySchemeType.APIKEY,
    in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER,
    paramName = "X-API-KEY",
    description = "API key for authentication. Add 'X-API-KEY' header with your API key."
)
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    private final ProductUsecase productUsecase;

    public ProductController(ProductUsecase productUsecase) {
        this.productUsecase = productUsecase;
    }

    @Operation(summary = "Get all products",
               description = "Retrieves a list of all products in the system",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Object getProducts(
            @RequestParam(value = "category_id", required = false) String categoryId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
            if (categoryId != null && !categoryId.isEmpty()) {
                return productUsecase.getProductsByCategory(categoryId);
            }
        
            if (search != null && !search.isEmpty()) {
                return productUsecase.filterProduct(search, page);
            }
        
            return productUsecase.getAllProducts();
    }


    @Operation(summary = "Get product by ID",
               description = "Retrieves a specific product using its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the product"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProduct(
            @Parameter(description = "ID of the product to retrieve")
            @PathVariable String id) {
        return productUsecase.getProduct(id);
    }

    @Operation(summary = "Create a new product",
               description = "Creates a new product with the provided information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Product successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(
            @Parameter(description = "Product information for creation")
            @RequestBody ProductDTO productDTO) {
        return productUsecase.createProduct(productDTO);
    }

    @Operation(summary = "Update an existing product",
               description = "Updates an existing product with new information",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Product successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO updateProduct(
            @Parameter(description = "Updated product information")
            @RequestBody ProductDTO productDTO) {
        return productUsecase.updateProduct(productDTO);
    }

    @Operation(summary = "Delete a product",
               description = "Deletes a product by its ID",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(
            @Parameter(description = "ID of the product to delete")
            @PathVariable String id) {
        productUsecase.deleteProduct(id);
    }

    @Operation(summary = "Get products grouped by category",
               description = "Retrieves a list of all products grouped by category",
               security = { @SecurityRequirement(name = "api_key") }
               )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved products grouped by category")
    @GetMapping("/grouped-by-category")
    @ResponseStatus(HttpStatus.OK)
    public List<Document> getProductsGroupedByCategory() {
        return productUsecase.getAllGroupedByCategoryAggregation();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }
}