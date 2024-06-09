package com.team5.project2.cart.dto;

public class UpdateCartItemCount {

    private Integer cartItemNo;
    private Integer cartItemCount;
    private Integer cartItemTotalPrice;

    public UpdateCartItemCount(Integer cartItemNo, Integer cartItemCount, Integer productPrice) {
        this.cartItemNo = cartItemNo;
        this.cartItemCount = cartItemCount;
        this.cartItemTotalPrice = productPrice*cartItemCount;
    }

    public Integer getCartItemNo() {
        return cartItemNo;
    }

    public Integer getCartItemCount() {
        return cartItemCount;
    }

    public Integer getCartItemTotalPrice() {
        return cartItemTotalPrice;
    }
}
