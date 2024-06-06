package com.team5.project2.category.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String listCategories(Model model) {
        List<CategoryDTO> categories = getFixedCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categories.isEmpty() ? null : categories.get(0).getId());
        return "category/categories"; // 템플릿 경로와 일치하도록 수정
    }

    private List<CategoryDTO> getFixedCategories() {
        List<CategoryDTO> categories = new ArrayList<>();
        categories.add(new CategoryDTO(1L, "CPU", null, "카테고리"));
        categories.add(new CategoryDTO(2L, "쿨러/튜닝", null, "카테고리"));
        categories.add(new CategoryDTO(3L, "메인보드", null, "카테고리"));
        categories.add(new CategoryDTO(4L, "메모리", null, "카테고리"));
        categories.add(new CategoryDTO(5L, "그래픽카드", null, "카테고리"));
        categories.add(new CategoryDTO(6L, "SSD", null, "카테고리"));
        categories.add(new CategoryDTO(7L, "HDD", null, "카테고리"));
        categories.add(new CategoryDTO(8L, "케이스", null, "카테고리"));
        return categories;
    }

    @GetMapping("/{parentId}/manufacturers")
    @ResponseBody
    public List<CategoryDTO> listManufacturers(@PathVariable Long parentId) {
        return categoryService.getSubCategories(parentId);
    }

    @PostMapping("/add")
    @ResponseBody
    public CategoryDTO addManufacturer(@RequestParam String name, @RequestParam Long parentId) {
        return categoryService.addCategory(name, parentId, "제조사");
    }

    @PostMapping("/edit")
    @ResponseBody
    public CategoryDTO editManufacturer(@RequestParam Long id, @RequestParam String name) {
        return categoryService.editCategory(id, name);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void deleteManufacturer(@RequestParam Long id) {
        categoryService.deleteCategory(id);
    }
}
