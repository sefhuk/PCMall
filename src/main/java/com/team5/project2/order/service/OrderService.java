package com.team5.project2.order.service;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderStatus;
import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrders(Long userId);
    List<OrderDetailDto> getOrderDetails(Long orderId);
    OrderDto findOrderById(Long id);
    OrderDto updateOrder(OrderDto orderDto);
    void deleteOrder(Long id);
}
