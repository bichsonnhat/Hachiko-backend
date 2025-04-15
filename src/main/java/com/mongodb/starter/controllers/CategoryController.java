package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import com.mongodb.starter.dtos.CategoryDTO;
import com.mongodb.starter.usecases.interfaces.CategoryUsecase;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;

@RestController
@RequestMapping("/categories")
@Tag(name = "categories")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class CategoryController {
        private final static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
        private final CategoryUsecase categoryUsecase;

        public CategoryController(CategoryUsecase categoryUsecase) {
                this.categoryUsecase = categoryUsecase;
        }

        @Operation(summary = "Get all categories", description = "Retrieves a list of all available categories", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all categories")
        @GetMapping("")
        @ResponseStatus(HttpStatus.OK)
        public List<CategoryDTO> getCategories() {
                return categoryUsecase.getCategories();
        }

        @Operation(summary = "Get category by ID", description = "Retrieves a specific category by its ID", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved the category"),
                        @ApiResponse(responseCode = "404", description = "Category not found")
        })
        @GetMapping("{id}")
        @ResponseStatus(HttpStatus.OK)
        public CategoryDTO getCategory(@PathVariable String id) {
                return categoryUsecase.getCategory(id);
        }

        @Operation(summary = "Create a new category", description = "Creates a new category with the provided information", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Category successfully created"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping("")
        @ResponseStatus(HttpStatus.CREATED)
        public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
                return categoryUsecase.createCategory(categoryDTO.toCategoryEntity());
        }

        @Operation(summary = "Update an existing category", description = "Updates an existing category with new information", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Category successfully updated"),
                        @ApiResponse(responseCode = "400", description = "Invalid input data"),
                        @ApiResponse(responseCode = "404", description = "Category not found")
        })
        @PutMapping("")
        @ResponseStatus(HttpStatus.OK)
        public CategoryDTO updateCategory(@RequestBody CategoryDTO categoryDTO) {
                return categoryUsecase.updateCategory(categoryDTO.toCategoryEntity());
        }

        @Operation(summary = "Delete a category", description = "Deletes a category by its ID", security = {
                        @SecurityRequirement(name = "api_key") })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Category successfully deleted"),
                        @ApiResponse(responseCode = "404", description = "Category not found")
        })
        @DeleteMapping("{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteCategory(@PathVariable String id) {
                categoryUsecase.deleteCategory(id);
        }

        @ExceptionHandler(RuntimeException.class)
        @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
        public final Exception handleAllExceptions(RuntimeException e) {
                LOGGER.error("Internal server error.", e);
                return e;
        }
}