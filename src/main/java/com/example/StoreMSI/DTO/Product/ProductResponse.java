package com.example.StoreMSI.DTO.Product;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private String barcode;
    private String imageUrl;

    private BigDecimal costPrice;
    private BigDecimal sellingPrice;
    private Integer quantity;

    private Long categoryId;
    private String categoryName;

    private Long brandId;
    private String brandName;
}