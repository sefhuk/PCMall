package com.team5.project2.cart.controller;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.service.CartService;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartViewController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/user/cart")
    public String getCart(Model model, @RequestParam(required = false) Long userId, HttpSession session) {
        Long currentUserId = getCurrentUserId();
        if (userId == null) {
            userId = currentUserId;
        }

        if (userId == null) {
            throw new RuntimeException("로그인되지 않은 사용자는 장바구니를 사용할 수 없습니다.");
        }

        CartDTO cart = cartService.getCart(userId);
        model.addAttribute("cart", cart);
        return "cart/cart";
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userService.findUserByEmail(email);
            return user.getId();
        }
        return null;
    }
}
