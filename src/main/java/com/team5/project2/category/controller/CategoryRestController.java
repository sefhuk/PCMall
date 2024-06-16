package com.team5.project2.category.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.CategoryType;
import com.team5.project2.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/{parentId}/manufacturers")
    public List<CategoryDTO> listManufacturers(@PathVariable Long parentId) {
        return categoryService.getSubCategories(parentId);
    }

    @PostMapping("/add")
    public CategoryDTO addManufacturer(@RequestParam String name, @RequestParam Long parentId) {
        return categoryService.addCategory(name, parentId, CategoryType.MANUFACTURER);
    }

    @PutMapping("/edit")
    public CategoryDTO editManufacturer(@RequestParam Long id, @RequestParam String name) {
        return categoryService.editCategory(id, name);
    }

    @DeleteMapping("/delete")
    public void deleteManufacturer(@RequestParam Long id) {categoryService.deleteCategory(id);
    }
}
