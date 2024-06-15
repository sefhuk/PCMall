package com.team5.project2.cart.controller;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestParam(required = false) Long userId, HttpSession session) {
        logger.info("GET /api/cart called with userId: {}", userId);
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        CartDTO cart = (userId != null) ? cartService.getCart(userId) : new CartDTO();
        logger.info("CartDTO returned: {}", cart);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestParam(required = false) Long userId, @RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        logger.info("POST /api/cart/add called with userId: {}, productId: {}, quantity: {}", userId, productId, quantity);
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        CartDTO cart;
        if (userId != null) {
            cart = cartService.addToCart(userId, productId, quantity);
        } else {
            cart = (CartDTO) session.getAttribute("cart");
            if (cart == null) {
                cart = new CartDTO();
            }
            cart = cartService.addToCart(userId, productId, quantity);
            session.setAttribute("cart", cart);
        }
        logger.info("CartDTO after add: {}", cart);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateCartItem(@RequestParam(required = false) Long userId, @RequestParam Long itemId, @RequestParam int quantity, HttpSession session) {
        logger.info("PUT /api/cart/update called with userId: {}, itemId: {}, quantity: {}", userId, itemId, quantity);
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        CartDTO cart;
        if (userId != null) {
            cart = cartService.updateCartItem(userId, itemId, quantity);
        } else {
            cart = (CartDTO) session.getAttribute("cart");
            if (cart == null) {
                cart = new CartDTO();
            }
            cart = cartService.updateCartItem(userId, itemId, quantity);
            session.setAttribute("cart", cart);
        }
        logger.info("CartDTO after update: {}", cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> removeCartItem(@RequestParam(required = false) Long userId, @RequestParam Long itemId, HttpSession session) {
        logger.info("DELETE /api/cart/remove called with userId: {}, itemId: {}", userId, itemId);
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        CartDTO cart;
        if (userId != null) {
            cart = cartService.removeCartItem(userId, itemId);
        } else {
            cart = (CartDTO) session.getAttribute("cart");
            if (cart == null) {
                cart = new CartDTO();
            }
            cart = cartService.removeCartItem(userId, itemId);
            session.setAttribute("cart", cart);
        }
        logger.info("CartDTO after remove: {}", cart);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam(required = false) Long userId, HttpSession session) {
        logger.info("DELETE /api/cart/clear called with userId: {}", userId);
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        if (userId != null) {
            cartService.clearCart(userId);
        }
        session.removeAttribute("cart");
        logger.info("Cart cleared for userId: {}", userId);
        return ResponseEntity.noContent().build();
    }
}
