package com.team5.project2.cart.service;

import com.team5.project2.cart.dao.CartDao;
import com.team5.project2.cart.dto.UpdateCartItemCount;
import com.team5.project2.cart.dto.request.CartRequest;
import com.team5.project2.cart.dto.CreateCartItem;
import com.team5.project2.cart.dto.response.CartResponse;
import org.springframework.stereotype.Service;
@Service
public class CartServiceImpl {

    private final CartDao cartDao;

    public CartServiceImpl(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public CartResponse.CartItemsDto getCart(Integer cartNo) {
        return new CartResponse.CartItemsDto(cartDao.selectCartItemsByCartNo(cartNo));
    }

    public CartResponse.CreateCartItemDto createCartItem(CartRequest.CreateCartItemDto requestDto) {
        CreateCartItem cCartItem = new CreateCartItem(requestDto);
        cartDao.insertCartItem(cCartItem);

        return new CartResponse.CreateCartItemDto(cCartItem.getCartItemNo());
    }

    public CartResponse.UpdateCartItemCountDto updateCartItemCount(CartRequest.UpdateCartItemCountDto requestDto) {
        Integer cartItemNo = requestDto.getCartItemNo();

        // 장바구니 아이템의 상품 번호로 상품 가격 조회
        Integer productPrice = cartDao.selectCartItemProductPrice(cartItemNo);

        // 장바구니 아이템 개수와 총 가격 수정 정보
        UpdateCartItemCount uCartItem = new UpdateCartItemCount(cartItemNo, requestDto.getCartItemCount(), productPrice);
        cartDao.updateCartItemCount(uCartItem);

        return new CartResponse.UpdateCartItemCountDto(uCartItem);
    }
}
