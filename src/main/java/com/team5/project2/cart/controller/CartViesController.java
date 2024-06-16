package com.team5.project2.cart.controller;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.service.CartService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class CartViesController {

    private final CartService cartService;

    @GetMapping("/cart")
    public String getCart(Model model, @RequestParam(required = false) Long userId, HttpSession session) {
        if (userId == null) {
            userId = (Long) session.getAttribute("userId");
        }
        CartDTO cart;
        if (userId != null) {
            cart = cartService.getCart(userId);
        } else {
            cart = (CartDTO) session.getAttribute("cart");
            if (cart == null) {
                cart = new CartDTO();
                session.setAttribute("cart", cart);
            }
        }
        model.addAttribute("cart", cart);
        return "cart/cart";
    }
}
