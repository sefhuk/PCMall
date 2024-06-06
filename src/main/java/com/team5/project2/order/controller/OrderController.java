package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/admin")
    public String adminHome(Model model) {
        List<OrderDto> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/adminHome";
    }

    @GetMapping("/{userId}")
    public String getUserOrders(@PathVariable Long userId, Model model) {
        List<OrderDto> orders = orderService.getOrders(userId);
        model.addAttribute("orders", orders);
        return "/order/orderList";
    }
}
