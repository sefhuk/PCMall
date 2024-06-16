package com.team5.project2.cart.dto.response;

import com.team5.project2.cart.dto.CartItem;
import com.team5.project2.cart.dto.UpdateCartItemCount;
import java.util.List;
import lombok.Data;

@Data
public class CartResponse {

    /* 장바구니 아이템 목록 DTO */
    @Data
    public static class CartItemsDto {

        private List<CartItem> cartItems; // 장바구니 목록 (장바구니 아이템 리스트)

        public CartItemsDto(List<CartItem> cartItems) {
            this.cartItems = cartItems;
        }

    }

    /* 장바구니 아이템 추가 DTO */
    @Data
    public static class CreateCartItemDto {

        private Long cartItemNo; // 장바구니 아이템 생성 후 부여된 시퀀스 리턴

        public CreateCartItemDto(Long cartItemNo) {
            this.cartItemNo = cartItemNo;
        }

    }

    /* 장바구니 아이템 수량 수정 DTO */
    @Data
    public static class UpdateCartItemCountDto {

        private Long cartItemNo;
        private Integer cartItemTotalPrice;

        public UpdateCartItemCountDto(UpdateCartItemCount uCartItemCount) {
            this.cartItemNo = uCartItemCount.getCartItemNo();
            this.cartItemTotalPrice = uCartItemCount.getCartItemTotalPrice();
        }

    }
}
