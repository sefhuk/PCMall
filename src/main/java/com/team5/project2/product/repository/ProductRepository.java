package com.team5.project2.product.repository;

import com.team5.project2.product.entity.Product;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"category", "images"})
    List<Product> findByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = {"category", "images"})
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);
}
