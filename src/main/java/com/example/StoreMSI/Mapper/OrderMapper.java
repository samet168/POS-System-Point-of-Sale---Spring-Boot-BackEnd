package com.example.StoreMSI.Mapper;

import com.example.StoreMSI.DTO.Order.OrderRequest;
import com.example.StoreMSI.DTO.Order.OrderResponse;
import com.example.StoreMSI.Model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest req) {
        if (req == null) return null;

        Order o = new Order();
        o.setTotalAmount(req.getTotalAmount());
        o.setReceivedAmount(req.getReceivedAmount());
        o.setChangeAmount(req.getChangeAmount());
        return o;
    }

    public OrderResponse toResponse(Order o) {
        if (o == null) return null;

        OrderResponse res = new OrderResponse();
        res.setId(o.getId());
        res.setOrderDate(o.getOrderDate());
        res.setTotalAmount(o.getTotalAmount());
        res.setReceivedAmount(o.getReceivedAmount());
        res.setChangeAmount(o.getChangeAmount());

        if (o.getUser() != null) {
            res.setUserId(o.getUser().getId());
            res.setUsername(o.getUser().getUsername());
        }

        return res;
    }
}