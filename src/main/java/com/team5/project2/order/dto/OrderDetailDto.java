package com.team5.project2.order.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long count;
    private Long price;
}
