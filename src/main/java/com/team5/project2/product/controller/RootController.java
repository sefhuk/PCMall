package com.team5.project2.product.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class RootController {

    private final CategoryService categoryService;

    @GetMapping
    @ResponseBody
    public String index(Model model) {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        return "index,, comming soon!";
    }
}
