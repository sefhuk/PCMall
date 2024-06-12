package com.team5.project2.product.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        @RequestParam(value = "category", defaultValue = "CPU") String category,
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
        Model model) {

        List<CategoryDTO> categories = categoryService.getAllCategories();

        CategoryDTO findCategory;
        try {
            findCategory = categoryService.getCategoryByName(category).get(0);
        } catch (Exception e) {
            throw new RuntimeException("카테고리를 찾을 수 없습니다.");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> pagedProducts = productService.findProductByCategoryIdPaging(findCategory.getId(), pageable);

        List<ProductResponseDto> products = pagedProducts.getContent().stream()
            .filter(p -> Objects.equals(p.getCategory().getName(), category))
            .map(ProductMapper.INSTANCE::productToProductResponseDto)
            .toList();

        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", pagedProducts.getTotalPages());

        return "product/product-list";
    }

    @GetMapping("/{id}")
    public String getProductDetailPage(@PathVariable Long id, Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        ProductResponseDto product = ProductMapper.INSTANCE.productToProductResponseDto(
            productService.findProduct(id));

        model.addAttribute("categories", categories);
        model.addAttribute("product", product);

        return "product/product-detail";
    }

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
