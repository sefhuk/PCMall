package com.team5.project2.product.service;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.team5.project2.product.dto.request.ProductRequestDto;
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

    private final Bucket bucket;

    public void addProduct(ProductRequestDto productRequestDto, List<MultipartFile> images)
        throws IOException {

        for (MultipartFile image : images) {
            byte[] bytes = image.getBytes();
            Blob blob = bucket.create(UUID.randomUUID().toString() + image.getOriginalFilename(),
                bytes,
                image.getContentType());
        }
    }
}
