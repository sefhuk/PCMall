package com.team5.project2.product.controller.admin;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/new")
    public String getProductSavePage(Model model) {

        List<CategoryDTO> parts = categoryService.getAllCategories();

        List<SelectOptionDto> options = new ArrayList<>();
        for (CategoryDTO category : parts) {
            for (CategoryDTO subCategory : categoryService.getSubCategories(category.getId())) {
                SelectOptionDto option = ProductMapper.INSTANCE.CategoryDtoToSelectOptionDto(
                    subCategory);
                option.setClassAttribute(getPartNameById(subCategory.getParentId()));
                options.add(option);
            }
        }

        model.addAttribute("parts", parts);
        model.addAttribute("options", options);

        return "product/product-create";
    }

    @GetMapping("/{id}/edit")
    public String getProductManagePage(@PathVariable("id") Long id, Model model) {

        ProductResponseDto product = ProductMapper.INSTANCE.productToProductResponseDto(
            productService.findProduct(id));

        List<CategoryDTO> brands = categoryService.getSubCategories(product.getCategoryId());

        model.addAttribute("brands", brands);
        model.addAttribute("product", product);

        return "product/product-manage";
    }

    private String getPartNameById(Long id) {
        if (id == 1L) return "cpu";
        if (id == 2L) return "cooler";
        if (id == 3L) return "mainboard";
        if (id == 4L) return "ram";
        if (id == 5L) return "gpu";
        if (id == 6L) return "ssd";
        if (id == 7L) return "hdd";
        return "case";
    }
}
