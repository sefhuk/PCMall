package com.team5.project2.product.controller.admin;

import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.entity.ProductImage;
import com.team5.project2.product.mapper.ProductImageMapper;
import com.team5.project2.product.mapper.ProductMapper;
import com.team5.project2.product.service.ProductService;
import com.team5.project2.product.util.JsonMapper;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class ProductAdminRestController {

    private final ProductService productService;
    private final JsonMapper jsonMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDto> productSave(
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @RequestParam("brand") String brand,
        @RequestParam("part") String part,
        @RequestParam("name") String name,
        @RequestParam("stock") Long stock,
        @RequestParam("price") Long price,
        @RequestParam("description") String description) throws IOException {

        Product newProduct = Product.builder().brand(brand).name(name).stock(stock).price(price)
            .description(jsonMapper.toMap(description))
            .build();

        Product product = productService.addProduct(newProduct, part, images);
        ProductResponseDto response = ProductMapper.INSTANCE.productToProductResponseDto(product);

        if (product.getImages() != null) {
            for (ProductImage image : product.getImages()) {
                response.getImages()
                    .add(ProductImageMapper.INSTANCE.productImageToProductImageResponseDto(image));
            }
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ProductResponseDto> productModify(
        @RequestPart(value = "images", required = false) List<MultipartFile> images,
        @RequestParam("productId") Long productId,
        @RequestParam("brand") String brand,
        @RequestParam("name") String name,
        @RequestParam("stock") Long stock,
        @RequestParam("price") Long price,
        @RequestParam("description") String description) throws IOException {

        Product product = productService.modifyProduct(productId, images, brand, name, stock, price,
            description);

        if (product == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(ProductMapper.INSTANCE.productToProductResponseDto(product));
    }


    @DeleteMapping
    public ResponseEntity<Void> productRemove(@RequestParam("productId") Long id) {

        Optional<Product> product = productService.removeProduct(id);

        if (product.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
