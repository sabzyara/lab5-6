package com.example.labuser2.mapper;

import com.example.labuser2.dto.CategoryDto;
import com.example.labuser2.entity.Category;

public class CategoryMapper {

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getCategoryId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryId(categoryDto.getCategoryId());
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }
}

