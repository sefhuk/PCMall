package com.team5.project2.cart.service;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.entity.Cart;
import com.team5.project2.cart.entity.CartItem;
import com.team5.project2.cart.mapper.CartMapper;
import com.team5.project2.cart.repository.CartRepository;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.repository.ProductRepository;
import com.team5.project2.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    public CartDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            return cartRepository.save(newCart);
        });
        return cartMapper.toDTO(cart);
    }

    public CartDTO addToCart(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.updateCartItem(cart,product,quantity);
            cart.getItems().add(newItem);
        }
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    public CartDTO updateCartItem(Long userId, Long itemId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = cart.getItems().stream().filter(ci -> ci.getId().equals(itemId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(quantity);
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    public CartDTO removeCartItem(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = cart.getItems().stream().filter(ci -> ci.getId().equals(itemId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));
        cart.getItems().remove(item);
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
