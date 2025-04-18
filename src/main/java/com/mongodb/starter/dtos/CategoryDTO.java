package com.mongodb.starter.dtos;

import org.bson.types.ObjectId;
import com.mongodb.starter.entity.CategoryEntity;

public record CategoryDTO(
        String id,
        String name,
        String imgUrl,
        boolean hasToppings) {

    public CategoryDTO(CategoryEntity categoryEntity) {
        this(categoryEntity.getId() == null ? new ObjectId().toHexString() : categoryEntity.getId().toHexString(),
                categoryEntity.getName(), categoryEntity.getImgUrl(), categoryEntity.isHasToppings());
    }

    public CategoryEntity toCategoryEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new CategoryEntity(_id, name, imgUrl, hasToppings);
    }
}
