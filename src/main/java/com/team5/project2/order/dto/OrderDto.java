package com.team5.project2.order.dto;

import com.team5.project2.order.entity.OrderStatus;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private OrderStatus status;
    private Long totalPrice;
    private List<OrderDetailDto> orderDetails;

    private String name;
    private String address;
    private String phoneNumber;

    private String createdAt;

    public void setCreatedAt(LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.createdAt = createdAt.format(formatter);
    }

    public void addOrderDetailDtos(List<OrderDetailDto> orderDetails) {
        this.orderDetails = orderDetails;
        for (OrderDetailDto orderDetail : orderDetails) {
            orderDetail.setOrderId(this.id);
        }
    }

    public Long getTotalPrice() {
        Long result = 0L;
        for (OrderDetailDto orderDetail : orderDetails) {
            result += orderDetail.getPrice() * orderDetail.getCount();
        }
        return result;
    }
}
