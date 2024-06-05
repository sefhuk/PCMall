package com.team5.project2.order.entity;

public enum OrderStatus {
    SHIPPING("배송 중"),
    DELIVERED("배송 완료"),
    CANCELED("취소"),
    CONFIRMED("주문 확인");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}

