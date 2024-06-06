package com.team5.project2.category.service;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.mapper.CategoryMapper;
import com.team5.project2.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

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
    public List<CategoryDTO> getCategoryByName(String name) {
        return categoryRepository.findByName(name).stream()
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
