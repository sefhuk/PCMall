package com.team5.project2.order.dto;

import com.team5.project2.order.entity.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private Long totalPrice;
}
