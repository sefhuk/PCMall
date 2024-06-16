package com.team5.project2.product.mapper;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.product.dto.response.ProductImageResponseDto;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.entity.ProductImage;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    SelectOptionDto CategoryDtoToSelectOptionDto(CategoryDTO categoryDTO);

    @Mapping(target = "images", expression = "java(mapImages(product.getImages()))")
    @Mapping(source = "category.name", target = "part")
    @Mapping(source = "category.id", target = "categoryId")
    ProductResponseDto productToProductResponseDto(Product product);

    default List<ProductImageResponseDto> mapImages(List<ProductImage> images) {
        return images.stream().map(ProductImageMapper.INSTANCE::productImageToProductImageResponseDto)
            .collect(Collectors.toList());
    }
}
