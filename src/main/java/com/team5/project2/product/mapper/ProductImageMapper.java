package com.team5.project2.product.mapper;

import com.team5.project2.product.dto.response.ProductImageResponseDto;
import com.team5.project2.product.entity.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductImageMapper {

    ProductImageMapper INSTANCE = Mappers.getMapper(ProductImageMapper.class);

    ProductImageResponseDto productImageToProductImageResponseDto(ProductImage productImage);
}
