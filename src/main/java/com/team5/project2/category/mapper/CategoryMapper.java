package com.team5.project2.category.mapper;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toCategoryDTO(Category category);
    Category toCategory(CategoryDTO categoryDTO);
}