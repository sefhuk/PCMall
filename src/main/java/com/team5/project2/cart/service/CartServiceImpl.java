package com.team5.project2.cart.service;

import com.team5.project2.cart.dao.CartRepository;
import com.team5.project2.cart.dto.CartItem;
import com.team5.project2.cart.dto.UpdateCartItemCount;
import com.team5.project2.cart.dto.request.CartRequest;
import com.team5.project2.cart.dto.CreateCartItem;
import com.team5.project2.cart.dto.response.CartResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartResponse.CartItemsDto getCart(long cartNo) {
        CartItem foundCartItem = cartRepository.findCartItemByCartItemNo(cartNo)
            .orElseThrow(() -> new RuntimeException("장바구니 아이템이 존재하지 않습니다."));

        return new CartResponse.CartItemsDto(Collections.singletonList(foundCartItem));
    }

    public CartResponse.CreateCartItemDto createCartItem(CartRequest.CreateCartItemDto requestDto) {
        CreateCartItem cCartItem = new CreateCartItem(requestDto);
        CartItem savedCartItem = cartRepository.save(cCartItem.toDomain());
        return new CartResponse.CreateCartItemDto(savedCartItem.getCartItemNo());
    }

    @Transactional
    public CartResponse.UpdateCartItemCountDto updateCartItemCount(CartRequest.UpdateCartItemCountDto requestDto) {
        long cartItemNo = requestDto.getCartItemNo();

        // 장바구니 아이템의 상품 번호로 상품 가격 조회
        CartItem foundCartItem = cartRepository.findCartItemByCartItemNo(cartItemNo)
            .orElseThrow(() -> new RuntimeException("장바구니 아이템이 존재하지 않습니다."));

        // 장바구니 아이템 개수와 총 가격 수정 정보
        UpdateCartItemCount uCartItem = new UpdateCartItemCount(cartItemNo, requestDto.getCartItemCount(), foundCartItem.getProductPrice());
        foundCartItem.update(uCartItem);

        return new CartResponse.UpdateCartItemCountDto(uCartItem);
    }
}
