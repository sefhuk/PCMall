package com.team5.project2.product.entity;

import com.team5.project2.category.entity.Category;
import com.team5.project2.common.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor
public class Product extends BaseTime {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String description;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private Long price;

    @OneToMany(mappedBy = "product", orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public Product(Long id, String name, String brand, String description, Long stock, Long price,
        List<ProductImage> images, Category category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.images = images;
        this.category = category;
    }

    public void addImage(ProductImage productImage) {
        images.add(productImage);
        productImage.updateProduct(this);
    }
}
