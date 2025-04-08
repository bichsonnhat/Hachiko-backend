package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.entity.CategoryEntity;
import com.mongodb.starter.repositories.interfaces.CategoryRepository;
import com.mongodb.starter.usecases.interfaces.CategoryUsecase;

@Service
public class CategoryUsecaseImpl implements CategoryUsecase {
    private final CategoryRepository categoryRepository;

    public CategoryUsecaseImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity createCategory(CategoryEntity categoryEntity) {
        return categoryRepository.insertOne(categoryEntity);
    }

    @Override
    public void deleteCategory(String id) {
        this.categoryRepository.deleteOne(id);
    }

    @Override
    public List<CategoryEntity> getCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public CategoryEntity getCategory(String id) {
        return this.categoryRepository.findOne(id);
    }

    @Override
    public CategoryEntity updateCategory(CategoryEntity entity) {
        return this.categoryRepository.updateOne(entity);
    }

}
