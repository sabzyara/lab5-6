package com.example.labuser2.controller;

import com.example.labuser2.entity.Category;
import com.example.labuser2.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@AllArgsConstructor
@Controller
@RequestMapping("/posgr/category")
public class CategoryController {
    private CategoryService categoryService;


    @PostMapping("/addCat")
    public String addCategory(@ModelAttribute Category category) {
        Category savedCategory = categoryService.addCategory(category);
        if (savedCategory != null) {
            return "redirect:/posgr/category/categories";
        } else {
            return "error";
        }
    }


    //    @GetMapping("{id}")
//    public ResponseEntity<CategoryDto> getCategorybyId(@PathVariable("id") Long category_id) {
//        CategoryDto categoryDto = categoryService.getCategorybyId(category_id);
//        return ResponseEntity.ok(categoryDto);
//    }
    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/categoriesUser")
    public String getCategoriesUser(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categoriesUser";
    }



    @PostMapping("{id}")
    public String updateCategory(@PathVariable("id") long category_id, @ModelAttribute Category updatedcategory) {
        Category category = categoryService.updateCategory(category_id, updatedcategory);
        if (category != null) {
            return "redirect:/posgr/category/categories";
        } else {
            return "error";
        }
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long category_id) {
        categoryService.deleteCategory(category_id);
        return ResponseEntity.ok("Deleted");
    }
}
