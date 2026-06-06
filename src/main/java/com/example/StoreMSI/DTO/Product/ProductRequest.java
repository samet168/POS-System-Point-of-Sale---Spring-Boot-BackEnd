package com.example.StoreMSI.DTO.Product;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank!")
    private String name;

    private String barcode;

    private String imageUrl;

    @NotNull(message = "Cost Price cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Cost Price must be greater than 0")
    private BigDecimal costPrice;

    @NotNull(message = "Selling Price cannot be null!")
    @DecimalMin(value = "0.0", inclusive = false, message = "Selling Price must be greater than 0")
    private BigDecimal sellingPrice;

    @NotNull(message = "Quantity cannot be null!")
    @Min(value = 0, message = "Quantity cannot be less than 0")
    private Integer quantity;

    @NotNull(message = "Please select a Category ID")
    private Long categoryId;

    @NotNull(message = "Please select a Brand ID")
    private Long brandId;
}