package com.team5.project2.product.controller;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;

import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @GetMapping
    public String getProductSavePage(Model model) {

        List<CategoryDTO> parts = categoryService.getAllCategories();

        List<SelectOptionDto> options = new ArrayList<>();
        for (CategoryDTO category : parts) {
            for (CategoryDTO subCategory : categoryService.getSubCategories(category.getId())) {
                SelectOptionDto option = productMapper.CategoryDtoToSelectOptionDto(
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

    @PostMapping
    @ResponseBody
    public ResponseEntity<Product> productSave(
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @RequestParam("brand") String brand,
        @RequestParam("part") String part,
        @RequestParam("name") String name,
        @RequestParam("stock") Long stock,
        @RequestParam("price") Long price,
        @RequestParam("description") String description) throws IOException {

        Product newProduct = Product.builder().brand(brand).name(name).stock(stock).price(price)
            .description(description)
            .build();

        productService.addProduct(newProduct, part, images);

        return ResponseEntity.ok().build();
    }
}
