package com.team5.project2.category.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.CategoryType;
import com.team5.project2.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categories.isEmpty() ? null : categories.get(0).getId());
        return "category/categories";
    }

    @GetMapping("/{parentId}/manufacturers")
    @ResponseBody
    public List<CategoryDTO> listManufacturers(@PathVariable Long parentId) {
        return categoryService.getSubCategories(parentId);
    }

    @PostMapping("/add")
    @ResponseBody
    public CategoryDTO addManufacturer(@RequestParam String name, @RequestParam Long parentId) {
        return categoryService.addCategory(name, parentId, CategoryType.MANUFACTURER);
    }

    @PutMapping("/edit")
    @ResponseBody
    public CategoryDTO editManufacturer(@RequestParam Long id, @RequestParam String name) {
        return categoryService.editCategory(id, name);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteManufacturer(@RequestParam Long id) {
        categoryService.deleteCategory(id);
    }
}
