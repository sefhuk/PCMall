package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.OrderStatus;
import com.team5.project2.order.service.OrderService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderService.findOrderById(id);
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
