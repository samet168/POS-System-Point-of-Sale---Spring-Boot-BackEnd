package com.example.StoreMSI.DTO.OrderItem;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemResponse {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;

    private Long orderId;
    private Long productId;

    private String productName;
}