package com.team5.project2.product.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDto {
    private String categoryId;
    private String brand;
    private String name;
    private Integer stock;
    private Integer price;
}
