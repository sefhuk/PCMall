package com.team5.project2.product.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponseDto {
    private String part;
    private String brand;
    private String name;
    private Long stock;
    private Long price;
    private List<ProductImageResponseDto> images = new ArrayList<>();
    private String description;
}
