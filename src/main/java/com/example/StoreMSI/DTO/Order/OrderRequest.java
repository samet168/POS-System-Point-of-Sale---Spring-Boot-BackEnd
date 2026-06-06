package com.example.StoreMSI.DTO.Order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {

    @NotNull(message = "Total amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private BigDecimal totalAmount;

    @NotNull(message = "Received amount is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Received amount cannot be negative")
    private BigDecimal receivedAmount;

    // Change amount អាចនឹងមិនចាំបាច់ផ្ញើមកពី Client ទេ
    // ព្រោះយើងគួរបង្កើត logic គណនានៅក្នុង Backend
    private BigDecimal changeAmount;

    @NotNull(message = "User ID is required")
    private Long userId;
}