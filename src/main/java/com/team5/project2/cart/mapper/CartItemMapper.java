package com.team5.project2.cart.mapper;

import com.team5.project2.cart.dto.CartItemDTO;
import com.team5.project2.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.price", target = "productPrice")
    CartItemDTO toDTO(CartItem cartItem);

    @Mapping(target = "product", ignore = true)  // 매핑에서 product를 무시
    CartItem toEntity(CartItemDTO cartItemDTO);
}