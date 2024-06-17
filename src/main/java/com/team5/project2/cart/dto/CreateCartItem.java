package com.team5.project2.cart.dto;
import com.team5.project2.cart.dto.request.CartRequest;
import lombok.Data;

@Data
/* 장바구니 아이템 생성 (Service -> Dao) DTO */
public class CreateCartItem {
    private Long cartItemNo;
    private Long productNo;
    private Long cartNo;
    private Integer cartItemCount;

    public CreateCartItem(CartRequest.CreateCartItemDto reqDto) {
        this.productNo = reqDto.getProductNo();
        this.cartNo = reqDto.getCartNo();
        this.cartItemCount = reqDto.getCartItemCount();
    }

    public CartItem toDomain() {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemNo(this.cartNo);
        cartItem.setProductNo(this.productNo);
        cartItem.setCartItemCount(this.cartItemCount);
        return cartItem;
    }

}
