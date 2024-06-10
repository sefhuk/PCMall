package com.team5.project2.cart.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemNo; // 장바구니 아이템 번호
    private Long productNo; // 상품 번호
    private String productName; // 상품 이름

    private Integer productPrice;
    private String productSimpleContent; // 상품 간단 설명
    private String productImgNewName; // 상품 이미지
    private Integer cartItemCount; // 장바구니 아이템 개수
    private Integer cartItemTotalPrice; // 장바구니 아이템 총 가격
    private Integer cartItemDiscountPrice; // 장바구니 아이템 할인된 가격

    public void update(UpdateCartItemCount updateCartItemCount) {
        this.cartItemCount = updateCartItemCount.getCartItemCount();
        this.cartItemTotalPrice = updateCartItemCount.getCartItemTotalPrice();
    }

}

