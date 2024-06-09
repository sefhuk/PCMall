package com.team5.project2.cart.dao;

import com.team5.project2.cart.dto.CartItem;
import com.team5.project2.cart.dto.CreateCartItem;
import com.team5.project2.cart.dto.UpdateCartItemCount;
import jakarta.persistence.MappedSuperclass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

//@MappedSuperclass
@Mapper//(componentModel = "spring")
public interface CartDao {
    CartDao INSTANCE = Mappers.getMapper(CartDao.class);

    List<CartItem> selectCartItemsByCartNo(Integer cartNo);

    Integer insertCartItem(CreateCartItem cartItem);

    Integer selectCartItemProductPrice(Integer productNo);

    void updateCartItemCount(UpdateCartItemCount cartItem);
}