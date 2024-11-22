package com.example.labuser2.service.impl;



import com.example.labuser2.entity.Category;
import com.example.labuser2.repository.CategoryRepository;
import com.example.labuser2.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class Categoryimpl implements CategoryService {
    private CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category){
        Category savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
    @Override
    public Category getCategorybyId(Long category_id){
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new RuntimeException("Category not found"));
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long category_id, Category updatedCategory) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());

        Category updatedCateO = categoryRepository.save(category);
        return updatedCateO;
    }

    @Override
    public void deleteCategory(Long category_id) {
        Category category = categoryRepository.findById(category_id).orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.deleteById(category_id);

    }

}
