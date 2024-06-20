package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.service.OrderService;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.service.UserService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public String adminHome(Model model, @PageableDefault(size = 10) Pageable pageable) {
        Page<OrderDto> orders = orderService.getAllOrders(pageable);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("totalPages", orders.getTotalPages());
        return "order/adminHome";
    }
}
