package com.team5.project2.product.mapper;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.product.dto.response.SelectOptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    SelectOptionDto CategoryDtoToSelectOptionDto(CategoryDTO categoryDTO);
}
