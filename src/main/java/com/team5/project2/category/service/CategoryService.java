package com.team5.project2.category.service;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.CategoryType;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();
    List<CategoryDTO> getSubCategories(Long parentId);
    CategoryDTO addCategory(String name, Long parentId, CategoryType type);
    CategoryDTO editCategory(Long id, String name);
    void deleteCategory(Long id);
    List<CategoryDTO> getCategoryByName(String name);
}
