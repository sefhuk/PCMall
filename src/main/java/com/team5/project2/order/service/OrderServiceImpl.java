package com.team5.project2.order.service;

import com.team5.project2.order.dto.OrderDetailDto;
import com.team5.project2.order.dto.OrderDto;
import com.team5.project2.order.dto.OrderRequest;
import com.team5.project2.order.entity.Order;
import com.team5.project2.order.entity.OrderDetail;
import com.team5.project2.order.entity.OrderStatus;
import com.team5.project2.order.exception.InsufficientStockException;
import com.team5.project2.order.mapper.OrderDetailMapper;
import com.team5.project2.order.mapper.OrderMapper;
import com.team5.project2.order.repository.OrderRepository;
import com.team5.project2.product.entity.Product;
import com.team5.project2.product.repository.ProductRepository;
import com.team5.project2.user.domain.User;
import com.team5.project2.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderDetailMapper orderDetailMapper;

    @Transactional
    public OrderDto createOrder(OrderRequest orderRequest, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setName(orderRequest.getName());
        order.setAddress(orderRequest.getAddress());
        order.setPhoneNumber(orderRequest.getPhoneNumber());
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailDto orderDetailDto : orderRequest.getOrderDetailDtos()) {
            Product product = productRepository.findById(orderDetailDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            // 재고 확인
            if (product.getStock() < orderDetailDto.getCount()) {
                throw new InsufficientStockException(product.getName());
            }

            // 재고 감소
            product.updateStock(-orderDetailDto.getCount());
            productRepository.save(product);

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setCount(orderDetailDto.getCount());
            orderDetail.setPrice(orderDetailDto.getPrice());

            orderDetails.add(orderDetail);
        }

        order.setOrderDetails(orderDetails);

        Order savedOrder = orderRepository.save(order);

        OrderDto orderDto = orderMapper.OrderToOrderDto(savedOrder);
        orderDto.setCreatedAt(savedOrder.getCreatedAt());
        return orderDto;
    }

    public Page<OrderDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
            .map(orderMapper::OrderToOrderDto);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " not found"));

        return orderMapper.OrderToOrderDto(order);
    }

    @Transactional
    public Page<OrderDto> getOrders(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
        return orderPage.map(orderMapper::OrderToOrderDto);
    }

    public List<OrderDetailDto> getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " not found"));

        List<OrderDetail> orderDetails = order.getOrderDetails();

        return orderDetails.stream()
            .map(orderDetail -> {
                OrderDetailDto dto = orderDetailMapper.OrderDetailToOrderDetailDto(orderDetail);
                Product product = orderDetail.getProduct();
                dto.setProductId(product.getId());
                dto.setProductName(product.getName());
                return dto;
            })
            .collect(Collectors.toList());
    }

    public OrderDto findOrderById(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("orderId " + id + ": not found"));

        return orderMapper.OrderToOrderDto(order);
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getId())
            .orElseThrow(() -> new RuntimeException("order not found"));
        order.setStatus(orderDto.getStatus());
        if (orderDto.getStatus().equals(OrderStatus.CANCELED)) {
            returnProducts(order.getOrderDetails());
        }
        order = orderRepository.save(order);

        OrderDto savedOrderDto = orderMapper.OrderToOrderDto(order);
        return savedOrderDto;
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order with ID " + id + " not found"));

        List<OrderDetail> orderDetails = order.getOrderDetails();
        returnProducts(orderDetails);

        orderRepository.deleteById(id);
    }

    public void returnProducts(List<OrderDetail> orderDetails) {
        if (orderDetails == null || orderDetails.size() == 0) return;
        for (OrderDetail orderDetail : orderDetails) {
            Product product = productRepository.findById(orderDetail.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            product.updateStock(orderDetail.getCount());
        }
    }
}
