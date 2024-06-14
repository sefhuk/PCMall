package com.team5.project2.product.entity;

import com.team5.project2.category.entity.Category;
import com.team5.project2.common.entity.BaseTime;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.CascadeType;
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
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseTime {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Type(JsonType.class)
    @Column(nullable = false, columnDefinition = "longtext")
    private Map<String, String> description;

    @Column(nullable = false)
    private Long stock;

    @Column(nullable = false)
    private Long price;

    @Builder.Default
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void addImage(ProductImage productImage) {
        images.add(productImage);
        productImage.updateProduct(this);
    }

    public void updateImage(ProductImage productImage, int index) {
        try {
            images.set(index, productImage);
        } catch (IndexOutOfBoundsException e) {
            images.add(productImage);
        }
    }

    public void updateStock(Long quantity) {
        stock += quantity;

        if (stock < 0) {
            stock = 0L;
        }
    }

    public void updateProduct(String name, String brand, Map<String, String> description, Long stock,
        Long price) {
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }
}
