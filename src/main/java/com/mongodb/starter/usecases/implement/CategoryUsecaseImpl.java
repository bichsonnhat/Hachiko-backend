package com.mongodb.starter.usecases.implement;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.starter.dtos.CategoryDTO;
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
    public CategoryDTO createCategory(CategoryEntity categoryEntity) {
        CategoryEntity savedEntity = categoryRepository.insertOne(categoryEntity);
        return new CategoryDTO(savedEntity);
    }

    @Override
    public void deleteCategory(String id) {
        this.categoryRepository.deleteOne(id);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> entities = this.categoryRepository.findAll();
        return entities.stream().map(CategoryDTO::new).toList();
    }

    @Override
    public CategoryDTO getCategory(String id) {
        CategoryEntity entity = this.categoryRepository.findOne(id);
        if (entity == null) {
            return null;
        }
        return new CategoryDTO(entity);
    }

    @Override
    public CategoryDTO updateCategory(CategoryEntity entity) {
        CategoryEntity updatedEntity = this.categoryRepository.updateOne(entity);
        if (updatedEntity == null) {
            return null;
        }
        return new CategoryDTO(updatedEntity);
    }

}
