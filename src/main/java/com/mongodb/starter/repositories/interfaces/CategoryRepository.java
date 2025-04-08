package com.mongodb.starter.repositories.interfaces;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mongodb.starter.entity.CategoryEntity;

@Repository
public interface CategoryRepository {
    CategoryEntity insertOne(CategoryEntity categoryEntity);

    void deleteOne(String id);

    List<CategoryEntity> findAll();

    CategoryEntity findOne(String id);

    CategoryEntity updateOne(CategoryEntity entity);
}
