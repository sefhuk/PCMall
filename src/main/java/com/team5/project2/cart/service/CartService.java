package com.team5.project2.cart.service;

import com.team5.project2.cart.dto.request.CartRequest;
import com.team5.project2.cart.dto.response.CartResponse;

public interface CartService {

    /* 사용자 장바구니 목록 조회 */
    CartResponse.CartItemsDto getCart(long cartNo);

    /* 장바구니 상품 추가 */
    CartResponse.CreateCartItemDto createCartItem(CartRequest.CreateCartItemDto requestDto);

    /* 장바구니 상품 개수 추가 */
    CartResponse.UpdateCartItemCountDto updateCartItemCount(CartRequest.UpdateCartItemCountDto requestDto);
}
