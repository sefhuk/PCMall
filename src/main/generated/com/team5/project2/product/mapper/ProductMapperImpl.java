package com.team5.project2.product.mapper;

import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.entity.Category;
import com.team5.project2.product.dto.response.ProductResponseDto;
import com.team5.project2.product.dto.response.SelectOptionDto;
import com.team5.project2.product.entity.Product;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-14T17:03:53+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public SelectOptionDto CategoryDtoToSelectOptionDto(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        SelectOptionDto.SelectOptionDtoBuilder selectOptionDto = SelectOptionDto.builder();

        selectOptionDto.name( categoryDTO.getName() );

        return selectOptionDto.build();
    }

    @Override
    public ProductResponseDto productToProductResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponseDto.ProductResponseDtoBuilder productResponseDto = ProductResponseDto.builder();

        productResponseDto.part( productCategoryName( product ) );
        productResponseDto.categoryId( productCategoryId( product ) );
        productResponseDto.id( product.getId() );
        productResponseDto.brand( product.getBrand() );
        productResponseDto.name( product.getName() );
        productResponseDto.stock( product.getStock() );
        productResponseDto.price( product.getPrice() );
        Map<String, String> map = product.getDescription();
        if ( map != null ) {
            productResponseDto.description( new LinkedHashMap<String, String>( map ) );
        }

        productResponseDto.images( mapImages(product.getImages()) );

        return productResponseDto.build();
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        Long id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
