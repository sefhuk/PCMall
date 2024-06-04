package com.team5.project2.product.controller;

import com.google.cloud.storage.Bucket;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.repository.CategoryRepository;
import com.team5.project2.product.dto.request.CpuRequestDto;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String getProductSavePage() {
        return "product/product-create";
    }

    @PostMapping
    @ResponseBody
    public String  productSave(@RequestPart("desc") CpuRequestDto cpuRequestDto,
        @RequestPart(value = "images", required = false) List<MultipartFile> images) {

//        return cpuRequestDto;
        return images.get(0).getOriginalFilename();
    }


}
