package com.team5.project2.cart.controller;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.service.CartService;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class CartViesController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping("/user/cart")
    public String getCart(Model model, HttpSession session, Principal principal) {

        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);
        CartDTO cart;

        if (user.getId() != null) {
            cart = cartService.getCart(user.getId());
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
