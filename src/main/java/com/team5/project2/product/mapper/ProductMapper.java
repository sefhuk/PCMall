package com.team5.project2.product.mapper;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    SelectOptionDto CategoryDtoToSelectOptionDto(CategoryDTO categoryDTO);

    @Mapping(target = "images", ignore = true)
    @Mapping(source = "category.name", target = "part")
    ProductResponseDto productToProductResponseDto(Product product);
}
