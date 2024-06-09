package com.team5.project2.product.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductViewController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping
    public String getProductListPage(
        @RequestParam(value = "category", defaultValue = "CPU") String category, Model model) {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        List<ProductResponseDto> products = productService.findProductAll().stream()
            .filter(p -> Objects.equals(p.getCategory().getName(), category))
            .map(ProductMapper.INSTANCE::productToProductResponseDto)
            .toList();

        model.addAttribute("categories", categories);
        model.addAttribute("products", products);

        return "product/product-list";
    }



    @GetMapping("/new")
    public String getProductSavePage(Model model) {

        List<CategoryDTO> parts = categoryService.getAllCategories();

        List<SelectOptionDto> options = new ArrayList<>();
        for (CategoryDTO category : parts) {
            for (CategoryDTO subCategory : categoryService.getSubCategories(category.getId())) {
                SelectOptionDto option = ProductMapper.INSTANCE.CategoryDtoToSelectOptionDto(
                    subCategory);

                if (subCategory.getParentId() == 1L) {
                    option.setClassAttribute("cpu");
                } else if (subCategory.getParentId() == 2L) {
                    option.setClassAttribute("cooler");
                } else if (subCategory.getParentId() == 3L) {
                    option.setClassAttribute("mainboard");
                } else if (subCategory.getParentId() == 4L) {
                    option.setClassAttribute("ram");
                } else if (subCategory.getParentId() == 5L) {
                    option.setClassAttribute("gpu");
                } else if (subCategory.getParentId() == 6L) {
                    option.setClassAttribute("ssd");
                } else if (subCategory.getParentId() == 7L) {
                    option.setClassAttribute("hdd");
                } else {
                    option.setClassAttribute("case");
                }

                options.add(option);
            }
        }

        model.addAttribute("parts", parts);
        model.addAttribute("options", options);

        return "product/product-create";
    }
}
