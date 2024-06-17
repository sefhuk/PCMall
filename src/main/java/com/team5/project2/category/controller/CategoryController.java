package com.team5.project2.category.controller;

import com.team5.project2.cart.dto.CartDTO;
import com.team5.project2.cart.service.CartService;
import com.team5.project2.category.dto.CategoryDTO;
import com.team5.project2.category.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CartService cartService;

    @GetMapping
    public String listCategories(Model model, HttpSession session) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", categories.isEmpty() ? null : categories.get(0).getId());

        // Cart 객체를 모델에 추가
        Long userId = (Long) session.getAttribute("userId");
        CartDTO cart = (userId != null) ? cartService.getCart(userId) : (CartDTO) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartDTO();
        }
        model.addAttribute("cart", cart);

        return "category/categories";
    }
}
