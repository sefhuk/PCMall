package com.team5.project2.product.repository;

import com.team5.project2.product.entity.ProductImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    Optional<ProductImage> findByName(String name);
}
