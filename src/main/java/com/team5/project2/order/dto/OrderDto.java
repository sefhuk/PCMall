package com.team5.project2.order.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private String status;
    private Long refundId;
}
