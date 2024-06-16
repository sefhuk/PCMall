package com.team5.project2.cart.dto.request;

import lombok.Data;

@Data
public class CartRequest {

    @Data
    /* 장바구니 아이템 생성 요청 DTO */
    public static class CreateCartItemDto {

        private Long productNo;
        private Long cartNo;
        private Integer cartItemCount;

    }

    @Data
    /* 장바구니 아이템 개수 수정 DTO */
    public static class UpdateCartItemCountDto {

        private Long cartItemNo;
        private Integer cartItemCount;

    }
}
