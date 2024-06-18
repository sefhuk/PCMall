package com.team5.project2.order.exception;

public class InsufficientStockException extends RuntimeException {
    private String productName;

    public InsufficientStockException(String productName) {
        super("Not enough stock for product: " + productName);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
}

