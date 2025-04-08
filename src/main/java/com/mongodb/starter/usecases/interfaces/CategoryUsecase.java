package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import com.mongodb.starter.entity.CategoryEntity;

public interface CategoryUsecase {
    CategoryEntity createCategory(CategoryEntity categoryEntity);

    List<CategoryEntity> getCategories();

    CategoryEntity getCategory(String id);

    CategoryEntity updateCategory(CategoryEntity entity);

    void deleteCategory(String id);
}
