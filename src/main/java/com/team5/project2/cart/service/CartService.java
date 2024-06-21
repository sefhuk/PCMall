package com.team5.project2.cart.service;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.entity.Cart;
import com.team5.project2.cart.entity.CartItem;
import com.team5.project2.cart.mapper.CartMapper;
import com.team5.project2.cart.repository.CartItemRepository;
import com.team5.project2.cart.repository.CartRepository;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.repository.ProductRepository;
import com.team5.project2.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;
    private final CartItemRepository cartItemRepository;

    public CartDTO getCart(Long userId) {
        System.out.println("Fetching cart for user ID: " + userId);
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")));
            return cartRepository.save(newCart);
        });
        return cartMapper.toDTO(cart);
    }

    public CartDTO addToCart(Long userId, Long productId, int quantity) {
        System.out.println("Adding to cart for user ID: " + userId + ", product ID: " + productId + ", quantity: " + quantity);
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
            newItem.updateCartItem(cart, product, quantity);
            cart.getItems().add(newItem);
        }
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    public CartDTO updateCartItem(Long userId, Long itemId, int quantity) {
        System.out.println("Updating cart item for user ID: " + userId + ", item ID: " + itemId + ", quantity: " + quantity);
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = cart.getItems().stream().filter(ci -> ci.getId().equals(itemId)).findFirst().orElseThrow(() -> new RuntimeException("Item not found"));
        item.setQuantity(quantity);
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toDTO(updatedCart);
    }

    public void removeCartItem(Long userId, Long itemId) {
        System.out.println("Removing item with ID " + itemId + " from cart for user ID " + userId);
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        CartItem item = cartItemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
        cart.removeItem(item);
        cartItemRepository.delete(item);
        cartRepository.save(cart);
        System.out.println("Item with ID " + itemId + " successfully removed from cart for user ID " + userId);
    }

    public void clearCart(Long userId) {
        System.out.println("Clearing cart for user ID: " + userId);
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public void removeCartItemByProductId(Long userId, Long productId) {
        System.out.println("Removing item with product ID " + productId + " from cart for user ID " + userId);
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Cart not found"));
        Optional<CartItem> itemOptional = cartItemRepository.findByProductId(productId);
        if (itemOptional.isPresent()) {
            CartItem item = itemOptional.get();
            cart.removeItem(item);
            cartItemRepository.delete(item);
            cartRepository.save(cart);
            System.out.println("Item with product ID " + productId + " successfully removed from cart for user ID " + userId);
        } else {
            System.out.println("Item with product ID " + productId + " not found in cart for user ID " + userId);
//            throw new RuntimeException("Item not found");
        }
    }
}
