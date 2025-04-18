package com.mongodb.starter.usecases.interfaces;

import java.util.List;

import com.mongodb.starter.dtos.CategoryDTO;
import com.mongodb.starter.entity.CategoryEntity;

public interface CategoryUsecase {
    CategoryDTO createCategory(CategoryEntity categoryEntity);

    List<CategoryDTO> getCategories();

    CategoryDTO getCategory(String id);

    CategoryDTO updateCategory(CategoryEntity entity);

    void deleteCategory(String id);
}
