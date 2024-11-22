package com.example.labuser2.service;


import com.example.labuser2.entity.Category;

import java.util.List;

public interface CategoryService {
    Category addCategory (Category category);
    Category getCategorybyId(Long category_id);
    List<Category> getAllCategories();
    Category updateCategory (Long category_id , Category updatedCategory);
    void deleteCategory (Long category_id);
}
