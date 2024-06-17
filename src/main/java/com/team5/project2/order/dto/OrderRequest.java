package com.team5.project2.order.dto;

import java.util.List;
import lombok.Data;

@Data
public class OrderRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private List<OrderDetailDto> orderDetailDtos;
}