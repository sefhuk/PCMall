package com.team5.project2.category.mapper;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.Category;
import com.team5.project2.category.entity.CategoryType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-17T20:56:55+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryDTO toCategoryDTO(Category category) {
        if ( category == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        Long parentId = null;
        CategoryType type = null;

        id = category.getId();
        name = category.getName();
        parentId = category.getParentId();
        type = category.getType();

        CategoryDTO categoryDTO = new CategoryDTO( id, name, parentId, type );

        return categoryDTO;
    }

    @Override
    public Category toCategory(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.id( categoryDTO.getId() );
        category.name( categoryDTO.getName() );
        category.parentId( categoryDTO.getParentId() );
        category.type( categoryDTO.getType() );

        return category.build();
    }
}
