package com.example.StoreMSI.DTO.OrderItem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be greater than 0")
    private Double unitPrice;

    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Product ID is required")
    private Long productId;
}