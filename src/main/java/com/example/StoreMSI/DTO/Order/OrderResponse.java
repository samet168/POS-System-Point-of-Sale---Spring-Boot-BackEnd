package com.example.StoreMSI.DTO.Order;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {

    private Long id;
    private LocalDateTime orderDate;

    private BigDecimal totalAmount;
    private BigDecimal receivedAmount;
    private BigDecimal changeAmount;

    private Long userId;
    private String username;
}