package com.mongodb.starter.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.CategoryDTO;
import com.mongodb.starter.entity.CategoryEntity;
import com.mongodb.starter.usecases.interfaces.CategoryUsecase;

@RestController
@RequestMapping("categories")
public class CategoryController {
    private final static Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryUsecase categoryUsecase;

    public CategoryController(CategoryUsecase categoryUsecase) {
        this.categoryUsecase = categoryUsecase;
    }

    @GetMapping("get")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryEntity> getCategories() {
        return categoryUsecase.getCategories();
    }

    @GetMapping("get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryEntity getCategory(@PathVariable String id) {
        return categoryUsecase.getCategory(id);
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryEntity createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryUsecase.createCategory(categoryDTO.toCategoryEntity());
    }

    @PutMapping("update")
    @ResponseStatus(HttpStatus.OK)
    public CategoryEntity updateCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryUsecase.updateCategory(categoryDTO.toCategoryEntity());
    }

    @DeleteMapping("delete/{id}")
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
