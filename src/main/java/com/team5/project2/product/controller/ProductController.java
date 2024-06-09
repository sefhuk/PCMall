package com.team5.project2.product.controller;

import com.team5.project2.category.service.CategoryService;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.entity.ProductImage;
import com.team5.project2.product.mapper.ProductImageMapper;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    private final ProductMapper productMapper;
    private final ProductImageMapper productImageMapper;

    @PostMapping
    @ResponseBody
    public ResponseEntity<ProductResponseDto> productSave(
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

        Product product = productService.addProduct(newProduct, part, images);
        ProductResponseDto response = productMapper.productToProductResponseDto(product);

        if (product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                response.getImages()
                    .add(productImageMapper.productImageToProductImageResponseDto(image));
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
