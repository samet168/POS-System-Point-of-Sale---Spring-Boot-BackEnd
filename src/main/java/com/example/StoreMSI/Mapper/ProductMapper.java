package com.example.StoreMSI.Mapper;

import com.example.StoreMSI.DTO.Product.ProductRequest;
import com.example.StoreMSI.DTO.Product.ProductResponse;
import com.example.StoreMSI.Model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Value("${file.base-url:http://localhost:8080/uploads/}")
    private String baseUrl;

    public Product toEntity(ProductRequest request) {
        if (request == null) return null;

        Product product = new Product();
        product.setName(request.getName());
        product.setBarcode(request.getBarcode());
        product.setImageUrl(request.getImageUrl());
        product.setCostPrice(request.getCostPrice());
        product.setSellingPrice(request.getSellingPrice());
        product.setQuantity(request.getQuantity());

        return product;
    }

    public ProductResponse toResponse(Product product) {
        if (product == null) return null;

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setBarcode(product.getBarcode());

        // ✅ IMPORTANT: FULL URL FOR CHROME
        response.setImageUrl(
                product.getImageUrl() != null
                        ? baseUrl + product.getImageUrl()
                        : null
        );

        response.setCostPrice(product.getCostPrice());
        response.setSellingPrice(product.getSellingPrice());
        response.setQuantity(product.getQuantity());

        if (product.getCategory() != null) {
            response.setCategoryId(product.getCategory().getId());
            response.setCategoryName(product.getCategory().getName());
        }

        if (product.getBrand() != null) {
            response.setBrandId(product.getBrand().getId());
            response.setBrandName(product.getBrand().getName());
        }

        return response;
    }
}