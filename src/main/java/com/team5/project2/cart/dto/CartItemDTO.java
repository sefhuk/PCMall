package com.team5.project2.cart.dto;

import com.team5.project2.product.entity.Product;
import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private double productPrice;
    private int quantity;
    private Product product;
}