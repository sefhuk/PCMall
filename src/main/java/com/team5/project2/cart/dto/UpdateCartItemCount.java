package com.team5.project2.cart.dto;

import lombok.Data;

@Data
public class UpdateCartItemCount {

    private Long cartItemNo;
    private Integer cartItemCount;
    private Integer cartItemTotalPrice;

    public UpdateCartItemCount(Long cartItemNo, Integer cartItemCount, Integer productPrice) {
        this.cartItemNo = cartItemNo;
        this.cartItemCount = cartItemCount;
        this.cartItemTotalPrice = productPrice*cartItemCount;
    }

}
