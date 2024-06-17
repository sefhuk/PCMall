package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.service.OrderService;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.service.ProductService;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/sheet")
    public String viewOrder(Principal principal, @RequestParam List<Long> productIds, @RequestParam List<Long> counts, Model model) {
        String userEmail = principal.getName();
        User user = userService.findUserByEmail(userEmail);

        List<OrderDetailDto> orderDetailDtos = new ArrayList<>();
        for (int i = 0; i < productIds.size(); i++) {
            Product product = productService.findProduct(productIds.get(i));
            OrderDetailDto orderDetailDto = new OrderDetailDto();
            orderDetailDto.setProductId(productIds.get(i));
            orderDetailDto.setProductName(product.getName());
            orderDetailDto.setCount(counts.get(i));
            orderDetailDto.setPrice(product.getPrice());
            orderDetailDtos.add(orderDetailDto);
        }

        model.addAttribute("name", user.getName());
        model.addAttribute("address", user.getAddress());
        model.addAttribute("phoneNumber", user.getPhone_number());
        model.addAttribute("orderDetails", orderDetailDtos);
        model.addAttribute("userId", user.getId());

        return "order/orderSheet";
    }


    @GetMapping
    public String getUserOrders(Principal principal, Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "8") int size) {
        String userEmail = principal.getName();
        Long userId = userService.findUserByEmail(userEmail).getId();

        Page<OrderDto> orderPage = orderService.getOrders(userId, page, size);
        model.addAttribute("orders", orderPage.getContent());
        model.addAttribute("currentPage", orderPage.getNumber());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        return "order/orderList";
    }

    @GetMapping("/detail/{orderId}")
    public String showDetail(@PathVariable Long orderId, Model model) {
        OrderDto order = orderService.getOrderById(orderId);
        List<OrderDetailDto> orderDetails = orderService.getOrderDetails(orderId);

        StringBuilder productIdsBuilder = new StringBuilder();
        StringBuilder countsBuilder = new StringBuilder();

        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetailDto detail = orderDetails.get(i);
            if (i > 0) {
                productIdsBuilder.append(',');
                countsBuilder.append(',');
            }
            productIdsBuilder.append(detail.getProductId());
            countsBuilder.append(detail.getCount());
        }

        String productIds = productIdsBuilder.toString();
        String counts = countsBuilder.toString();

        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderDetails);
        model.addAttribute("productIds", productIds);
        model.addAttribute("counts", counts);

        return "order/orderDetail";
    }

    @GetMapping("/addressForm")
    public String kakaoAddress() {
        return "order/getAddress";
    }
}