package com.team5.project2.order.controller;

import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.service.OrderServiceImpl;
import java.util.List;
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
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderServiceImpl orderServiceImpl;

    @Autowired
    public OrderRestController(OrderServiceImpl orderServiceImpl) {
        this.orderServiceImpl = orderServiceImpl;
    }

    @GetMapping
    public ResponseEntity getAllOrders() {
        List<OrderDto> orders = orderServiceImpl.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderServiceImpl.createOrder(orderDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto orderDto = orderServiceImpl.findOrderById(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);

    }

    @PutMapping("/{orderId}")
    public ResponseEntity updateOrder(@PathVariable Long orderId, @RequestBody String status) {
        OrderDto orderDto = orderServiceImpl.findOrderById(orderId);
        orderDto.setStatus(status);

        orderServiceImpl.updateOrder(orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderServiceImpl.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
