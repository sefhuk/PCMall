package com.team5.project2.order.service;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.dto.OrderRequest;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderStatus;
import java.util.List;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderDto createOrder(OrderRequest orderRequest, Long userId);
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long orderId);
    Page<OrderDto> getOrders(Long userId, int page, int size);
    List<OrderDetailDto> getOrderDetails(Long orderId);
    OrderDto findOrderById(Long id);
    OrderDto updateOrder(OrderDto orderDto);
    void deleteOrder(Long id);
}
