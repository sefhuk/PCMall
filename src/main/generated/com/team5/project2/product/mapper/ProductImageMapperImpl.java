package com.team5.project2.product.mapper;

import com.team5.project2.product.dto.response.ProductImageResponseDto;
import com.team5.project2.product.entity.ProductImage;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-17T20:56:55+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ProductImageMapperImpl implements ProductImageMapper {

    @Override
    public ProductImageResponseDto productImageToProductImageResponseDto(ProductImage productImage) {
        if ( productImage == null ) {
            return null;
        }

        ProductImageResponseDto.ProductImageResponseDtoBuilder productImageResponseDto = ProductImageResponseDto.builder();

        productImageResponseDto.id( productImage.getId() );
        productImageResponseDto.url( productImage.getUrl() );
        productImageResponseDto.name( productImage.getName() );

        return productImageResponseDto.build();
    }
}
