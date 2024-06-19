package com.team5.project2.cart.dto;

import java.util.List;
import lombok.Data;

@Data
public class CartDTO {
    private Long id;
    private Long userId;
    private List<CartItemDTO> items;
    private int totalQuantity;
    private double totalPrice;
}