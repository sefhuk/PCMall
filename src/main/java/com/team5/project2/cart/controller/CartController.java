package com.team5.project2.cart.controller;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@RequestParam(required = false) Long userId, HttpSession session) {
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        CartDTO cart = (userId != null) ? cartService.getCart(userId) : (CartDTO) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartDTO();
        }
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestParam(required = false) Long userId, @RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
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
            cart = cartService.addToCart(null, productId, quantity);
            session.setAttribute("cart", cart);
        }
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<CartDTO> updateCartItem(@RequestParam(required = false) Long userId, @RequestParam Long itemId, @RequestParam int quantity, HttpSession session) {
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
            cart = cartService.updateCartItem(null, itemId, quantity);
            session.setAttribute("cart", cart);
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> removeCartItem(@RequestParam(required = false) Long userId, @RequestParam Long itemId, HttpSession session) {
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
            cart = cartService.removeCartItem(null, itemId);
            session.setAttribute("cart", cart);
        }
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(@RequestParam(required = false) Long userId, HttpSession session) {
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        if (userId != null) {
            cartService.clearCart(userId);
        }
        session.removeAttribute("cart");
        return ResponseEntity.noContent().build();
    }
}
