package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderStatus;
import com.team5.project2.order.service.OrderService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{userId}/viewOrder")
    public String viewOrder(@PathVariable Long userId, @RequestParam List<Long> productIds, @RequestParam List<Long> counts, @RequestParam List<Long> prices, Model model) {
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            OrderDetailDto orderDetailDto = new OrderDetailDto();
//            orderDetailDto.setProductId(productIds.get(i));
            orderDetailDto.setCount(counts.get(i));
            orderDetailDto.setPrice(prices.get(i));
            orderDetails.add(orderDetailDto);
        }
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("userId", userId);
        return "order/order";
    }

    @GetMapping("/{userId}")
    public String getUserOrders(@PathVariable Long userId, Model model) {
        List<OrderDto> orders = orderService.getOrders(userId);
        model.addAttribute("orders", orders);
        return "/order/orderList";
    }

    @GetMapping("/detail/{orderId}")
    public String showDetail(@PathVariable Long orderId, Model model) {
        OrderDto order = orderService.getOrderById(orderId);
        List<OrderDetailDto> orderDetails = orderService.getOrderDetails(orderId);
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        return "/order/orderDetail";
    }
}
