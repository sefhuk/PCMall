package com.team5.project2.cart.mapper;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.dto.CartItemDTO;
import com.team5.project2.cart.entity.Cart;
import com.team5.project2.cart.entity.CartItem;
import com.team5.project2.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-21T13:25:36+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CartMapperImpl implements CartMapper {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public CartDTO toDTO(Cart cart) {
        if ( cart == null ) {
            return null;
        }

        CartDTO cartDTO = new CartDTO();

        cartDTO.setUserId( cartUserId( cart ) );
        cartDTO.setId( cart.getId() );
        cartDTO.setItems( cartItemListToCartItemDTOList( cart.getItems() ) );

        return cartDTO;
    }

    @Override
    public Cart toEntity(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        Cart cart = new Cart();

        cart.setUser( cartDTOToUser( cartDTO ) );
        if ( cart.getItems() != null ) {
            List<CartItem> list = cartItemDTOListToCartItemList( cartDTO.getItems() );
            if ( list != null ) {
                cart.getItems().addAll( list );
            }
        }

        return cart;
    }

    private Long cartUserId(Cart cart) {
        if ( cart == null ) {
            return null;
        }
        User user = cart.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected List<CartItemDTO> cartItemListToCartItemDTOList(List<CartItem> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItemDTO> list1 = new ArrayList<CartItemDTO>( list.size() );
        for ( CartItem cartItem : list ) {
            list1.add( cartItemMapper.toDTO( cartItem ) );
        }

        return list1;
    }

    protected User cartDTOToUser(CartDTO cartDTO) {
        if ( cartDTO == null ) {
            return null;
        }

        User user = new User();

        user.setId( cartDTO.getUserId() );

        return user;
    }

    protected List<CartItem> cartItemDTOListToCartItemList(List<CartItemDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<CartItem> list1 = new ArrayList<CartItem>( list.size() );
        for ( CartItemDTO cartItemDTO : list ) {
            list1.add( cartItemMapper.toEntity( cartItemDTO ) );
        }

        return list1;
    }
}
