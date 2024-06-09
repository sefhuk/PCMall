package com.team5.project2.category.service;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.entity.CategoryType;
import com.team5.project2.category.mapper.CategoryMapper;
import com.team5.project2.category.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;



    @PostConstruct
    @Transactional
    public void init() {
        if (categoryRepository.count() == 0) {
            List<Category> initialCategories = getFixedCategories().stream()
                .map(categoryMapper::toCategory)
                .collect(Collectors.toList());
            categoryRepository.saveAll(initialCategories);
        }
    }

    private List<CategoryDTO> getFixedCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(new CategoryDTO(1L, "CPU", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(2L, "쿨러/튜닝", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(3L, "메인보드", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(4L, "메모리", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(5L, "그래픽카드", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(6L, "SSD", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(7L, "HDD", null, CategoryType.CATEGORY));
        categories.add(new CategoryDTO(8L, "케이스", null, CategoryType.CATEGORY));
        return categories;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findByParentIdIsNull().stream()
            .map(categoryMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getSubCategories(Long parentId) {
        return categoryRepository.findByParentId(parentId).stream()
            .map(categoryMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getCategoryByName(String name) {
        return categoryRepository.findByName(name).stream()
            .map(categoryMapper::toCategoryDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategoryDTO addCategory(String name, Long parentId, CategoryType type) {
        Category category = new Category();
        category.update(name,parentId,type);
        return categoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryDTO editCategory(Long id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.update(name, category.getParentId(), category.getType());
        return categoryMapper.toCategoryDTO(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


}
