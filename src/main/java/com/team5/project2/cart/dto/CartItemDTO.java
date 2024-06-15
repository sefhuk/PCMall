package com.team5.project2.cart.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;

}