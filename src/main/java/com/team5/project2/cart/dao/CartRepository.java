package com.team5.project2.cart.dao;

import com.team5.project2.cart.dto.CartItem;
import com.team5.project2.cart.dto.CreateCartItem;
import com.team5.project2.cart.dto.UpdateCartItemCount;
import java.util.Optional;
import org.mapstruct.factory.Mappers;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findCartItemByCartItemNo(Long cartItemNo);

}
