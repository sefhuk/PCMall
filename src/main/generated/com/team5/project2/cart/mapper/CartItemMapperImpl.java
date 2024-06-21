package com.team5.project2.cart.mapper;

import com.team5.project2.cart.dto.CartItemDTO;
import com.team5.project2.cart.entity.CartItem;
import com.team5.project2.product.entity.Product;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T13:25:36+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CartItemMapperImpl implements CartItemMapper {

    @Override
    public CartItemDTO toDTO(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }

        CartItemDTO cartItemDTO = new CartItemDTO();

        cartItemDTO.setProductId( cartItemProductId( cartItem ) );
        cartItemDTO.setProductName( cartItemProductName( cartItem ) );
        Long price = cartItemProductPrice( cartItem );
        if ( price != null ) {
            cartItemDTO.setProductPrice( price );
        }
        cartItemDTO.setId( cartItem.getId() );
        cartItemDTO.setQuantity( cartItem.getQuantity() );
        cartItemDTO.setProduct( cartItem.getProduct() );

        return cartItemDTO;
    }

    @Override
    public CartItem toEntity(CartItemDTO cartItemDTO) {
        if ( cartItemDTO == null ) {
            return null;
        }

        CartItem cartItem = new CartItem();

        cartItem.setQuantity( cartItemDTO.getQuantity() );

        return cartItem;
    }

    private Long cartItemProductId(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String cartItemProductName(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long cartItemProductPrice(CartItem cartItem) {
        if ( cartItem == null ) {
            return null;
        }
        Product product = cartItem.getProduct();
        if ( product == null ) {
            return null;
        }
        Long price = product.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }
}
