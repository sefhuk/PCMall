package com.team5.project2.order.service;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderDetail;
import com.team5.project2.order.entity.OrderStatus;
import com.team5.project2.order.mapper.OrderDetailMapper;
import com.team5.project2.order.mapper.OrderMapper;
import com.team5.project2.order.repository.OrderDetailRepository;
import com.team5.project2.order.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDto);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.OrderToOrderDto(order);
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(OrderMapper.INSTANCE::OrderToOrderDto)
            .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " not found"));

        return OrderMapper.INSTANCE.OrderToOrderDto(order);
    }

    public List<OrderDto> getOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
            .map(OrderMapper.INSTANCE::OrderToOrderDto)
            .collect(Collectors.toList());
    }

    public List<OrderDetailDto> getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " not found"));

        List<OrderDetail> orderDetails = order.getOrderDetails();

        return orderDetails.stream()
            .map(OrderDetailMapper.INSTANCE::OrderDetailToOrderDetailDto)
            .collect(Collectors.toList());
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("orderId " + id + ": not found"));
        return OrderMapper.INSTANCE.OrderToOrderDto(order);
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        Order order = OrderMapper.INSTANCE.OrderDtoToOrder(orderDto);
        order = orderRepository.save(order);
        return OrderMapper.INSTANCE.OrderToOrderDto(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
