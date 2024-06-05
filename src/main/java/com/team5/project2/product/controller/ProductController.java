package com.team5.project2.product.controller;

import com.team5.project2.product.dto.request.ProductRequestDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Product> productSave(
        @RequestPart(value = "description", required = false) ProductRequestDto productRequestDto,
        @RequestPart(value = "images", required = false) List<MultipartFile> images)
        throws IOException {
        return ResponseEntity.ok(productService.addProduct(productRequestDto, images));
    }

}
