package com.team5.project2.product.mapper;

import com.team5.project2.product.dto.response.ProductImageResponseDto;
import com.team5.project2.product.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {
    ProductImageResponseDto productImageToProductImageResponseDto(ProductImage productImage);
}
