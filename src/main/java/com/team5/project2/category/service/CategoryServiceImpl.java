package com.team5.project2.category.service;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.mapper.CategoryMapper;
import com.team5.project2.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findByParentIdIsNull().stream()
            .map(categoryMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
            .map(categoryMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO addCategory(String name, Long parentId, String type) {
        Category category = new Category();
        category.update(name,parentId,type);
        return categoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO editCategory(Long id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.update(name, category.getParentId(), category.getType());
        return categoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
