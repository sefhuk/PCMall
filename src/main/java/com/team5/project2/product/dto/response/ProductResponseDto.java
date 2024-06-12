package com.team5.project2.product.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long id;
    private Long categoryId;
    private String part;
    private String brand;
    private String name;
    private Long stock;
    private Long price;
    private List<ProductImageResponseDto> images = new ArrayList<>();
    private Map<String, String> description;
}
