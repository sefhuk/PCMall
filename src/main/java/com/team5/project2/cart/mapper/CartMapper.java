package com.team5.project2.cart.mapper;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper {
    @Mapping(source = "user.id", target = "userId")
    CartDTO toDTO(Cart cart);

    @Mapping(target = "user.id", source = "userId")
    Cart toEntity(CartDTO cartDTO);
}