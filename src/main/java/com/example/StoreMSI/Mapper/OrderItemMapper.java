package com.example.StoreMSI.Mapper;

import com.example.StoreMSI.DTO.OrderItem.OrderItemRequest;
import com.example.StoreMSI.DTO.OrderItem.OrderItemResponse;
import com.example.StoreMSI.Model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {

    // ===== toEntity =====
    public OrderItem toEntity(OrderItemRequest req) {
        if (req == null) return null;

        OrderItem item = new OrderItem();
        item.setQuantity(req.getQuantity());
        item.setUnitPrice(
                req.getUnitPrice() != null
                        ? java.math.BigDecimal.valueOf(req.getUnitPrice())
                        : null
        );
        return item;
    }

    // ===== toResponse =====
    public OrderItemResponse toResponse(OrderItem item) {
        if (item == null) return null;

        OrderItemResponse res = new OrderItemResponse();
        res.setId(item.getId());
        res.setQuantity(item.getQuantity());
        res.setUnitPrice(item.getUnitPrice());

        if (item.getOrder() != null) {
            res.setOrderId(item.getOrder().getId());
        }

        if (item.getProduct() != null) {
            res.setProductId(item.getProduct().getId());
            res.setProductName(item.getProduct().getName());
        }

        return res;
    }
}