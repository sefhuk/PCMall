package com.team5.project2.product.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.repository.CategoryRepository;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.entity.ProductImage;
import com.team5.project2.product.repository.ProductImageRepository;
import com.team5.project2.product.repository.ProductRepository;
import com.team5.project2.product.util.JsonMapper;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryRepository categoryRepository;
    private final JsonMapper jsonMapper;
    private final Storage storage;
    private final Bucket bucket;

    public Product findProduct(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));
    }

    public List<Product> findProductAll(Long id) {
        return productRepository.findAll();
    }

    public List<Product> findProductByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    public Page<Product> findProductByCategoryIdPaging(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryId(categoryId, pageable);
    }

    public Product addProduct(Product product, String part, List<MultipartFile> images)
        throws IOException {

        Category category;
        try {
            category = categoryRepository.findByName(part).get(0);
            product.updateCategory(category);
        } catch (Exception e) {
            throw new RuntimeException("해당 부품 카테고리가 존재하지 않습니다.");
        }

        if (images != null) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);

                Blob blob = uploadImageFromStorage(image);

                ProductImage newImage = ProductImage.builder().url(blob.getMediaLink())
                    .name(blob.getName()).build();
                product.addImage(newImage);
            }
        }

        return productRepository.save(product);
    }

    public Product modifyProduct(Long productId, List<MultipartFile> images, String brand,
        String name, Long stock, Long price, String description) throws IOException {

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException(
                "해당 상품이 존재하지 않습니다."));


        for (MultipartFile image: images) {

            if (Objects.equals(image.getContentType(), "image/name")) {
                ProductImage productImage = productImageRepository.findByName(
                        image.getOriginalFilename())
                    .orElseThrow(() -> new RuntimeException("해당 이미지가 존재하지 않습니다."));

                product.updateImage(productImage, images.indexOf(image));

                continue;
            }

            Blob blob = uploadImageFromStorage(image);

            ProductImage newImage = ProductImage.builder().url(blob.getMediaLink())
                .name(blob.getName()).product(product).build();

            removeImageOfProduct(product, images.indexOf(image));
            product.updateImage(newImage, images.indexOf(image));
        }

        if (images.size() < 4) {
            for (int i = images.size(); i <= 3; i++) {
                try {
                    ProductImage trashImage = product.getImages().get(i);
                    removeImageFromStorage(trashImage);
                    product.getImages().remove(trashImage);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }

        product.updateProduct(name, brand, jsonMapper.toMap(description), stock, price);

        return productRepository.save(product);
    }

    public Optional<Product> removeProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));

        List<ProductImage> images = product.getImages();
        for (ProductImage image : images) {
            removeImageFromStorage(image);
        }

        productRepository.deleteById(id);

        return productRepository.findById(id);
    }

    private Blob uploadImageFromStorage(MultipartFile image) throws IOException {
        byte[] bytes = image.getBytes();
        return bucket.create(
            UUID.randomUUID().toString() + image.getOriginalFilename(),
            bytes,
            image.getContentType());
    }

    private void removeImageFromStorage(ProductImage productImage) {
        BlobId blobId = BlobId.of(bucket.getName(), productImage.getName());
        storage.delete(blobId);
    }

    private void removeImageOfProduct(Product product, int index) {
        try {
            ProductImage oldProductImage = product.getImages().get(index);
            removeImageFromStorage(oldProductImage);
        } catch (IndexOutOfBoundsException ignored) {
        }
    }
}
