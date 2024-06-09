package com.team5.project2.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.repository.CategoryRepository;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.entity.ProductImage;
import com.team5.project2.product.repository.ProductRepository;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final Bucket bucket;

    public Product addProduct(Product productRequestDto, String part, List<MultipartFile> images)
        throws IOException {

        Category category;
        try {
            category = categoryRepository.findByName(part).get(0);
        } catch (Exception e) {
            throw new RuntimeException("해당 부품 카테고리가 존재하지 않습니다.");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Product newProduct = Product.builder().name(productRequestDto.getName())
            .category(category)
            .price(productRequestDto.getPrice())
            .stock(productRequestDto.getStock())
            .description(objectMapper.writeValueAsString(productRequestDto.getDescription()))
            .brand(productRequestDto.getBrand()).build();

        if (images != null) {
            for (MultipartFile image : images) {
                byte[] bytes = image.getBytes();
                Blob blob = bucket.create(
                    UUID.randomUUID().toString() + image.getOriginalFilename(),
                    bytes,
                    image.getContentType());

                ProductImage newImage = ProductImage.builder().url(blob.getMediaLink()).build();
                newProduct.addImage(newImage);
            }
        }

        return productRepository.save(newProduct);
    }
}
