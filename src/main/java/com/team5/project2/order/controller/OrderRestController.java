package com.team5.project2.order.controller;

import com.team5.project2.cart.service.CartService;
import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.dto.OrderRequest;
import com.team5.project2.order.entity.OrderStatus;
import com.team5.project2.order.exception.InsufficientStockException;
import com.team5.project2.order.service.OrderService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/order")
public class OrderRestController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId, @RequestBody OrderRequest orderRequest) {
        System.out.println("orderRequest: " + orderRequest);
        try {
            OrderDto createdOrderDto = orderService.createOrder(orderRequest, userId);
            for (OrderDetailDto orderDetailDto : orderRequest.getOrderDetailDtos()) {
                cartService.removeCartItemByProductId(userId, orderDetailDto.getProductId());
            }
            return ResponseEntity.ok(createdOrderDto);
        } catch (InsufficientStockException e) {
            return ResponseEntity.badRequest().body("재고가 부족한 상품: " + e.getProductName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
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
