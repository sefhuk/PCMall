package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.dto.OrderRequest;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderDetail;
import com.team5.project2.order.entity.OrderStatus;
import com.team5.project2.order.mapper.OrderDetailMapper;
import com.team5.project2.order.mapper.OrderMapper;
import com.team5.project2.order.service.OrderService;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderRestController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable Long userId, @RequestBody OrderRequest orderRequest) {
        try {
            OrderDto createdOrderDto = orderService.createOrder(orderRequest, userId);
            return ResponseEntity.ok(createdOrderDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.findOrderById(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);

    }

    @Data
    @AllArgsConstructor
    static class Status {
        private String orderStatus;
        private String orderId;
    }
    @PutMapping("/{orderId}")
    public ResponseEntity updateOrder(@PathVariable Long orderId, @RequestBody Status status) {
        OrderDto orderDto = orderService.findOrderById(orderId);
        try {
            OrderStatus orderStatus = OrderStatus.fromString(status.orderStatus);
            orderDto.setStatus(orderStatus);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        orderService.updateOrder(orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
