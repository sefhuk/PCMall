package com.team5.project2.order.service;

import com.team5.project2.order.dto.OrderDto;
import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getAllOrders();
    List<OrderDto> getOrders(Long userId);
    OrderDto findOrderById(Long id);
    OrderDto updateOrder(OrderDto orderDto);
    void deleteOrder(Long id);
}
