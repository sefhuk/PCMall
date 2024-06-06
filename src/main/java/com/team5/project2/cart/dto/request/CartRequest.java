package com.team5.project2.cart.dto.request;

public class CartRequest {

    /* 장바구니 아이템 생성 요청 DTO */
    public static class CreateCartItemDto {

        private Integer productNo;
        private Integer cartNo;
        private Integer cartItemCount;

        public CreateCartItemDto() {}

        public Integer getProductNo() {
            return productNo;
        }

        public Integer getCartNo() {
            return cartNo;
        }

        public Integer getCartItemCount() {
            return cartItemCount;
        }

        public void setProductNo(Integer productNo) {
            this.productNo = productNo;
        }

        public void setCartNo(Integer cartNo) {
            this.cartNo = cartNo;
        }

        public void setCartItemCount(Integer cartItemCount) {
            this.cartItemCount = cartItemCount;
        }
    }

    /* 장바구니 아이템 개수 수정 DTO */
    public static class UpdateCartItemCountDto {

        private Integer cartItemCount;
        private Integer cartItemNo;

        public Integer getCartItemCount() {
            return cartItemCount;
        }

        public Integer getCartItemNo() {
            return cartItemNo;
        }

        public void setCartItemCount(Integer cartItemCount) {
            this.cartItemCount = cartItemCount;
        }

        public void setCartItemNo(Integer productNo) {
            this.cartItemNo = productNo;
        }
    }
}
