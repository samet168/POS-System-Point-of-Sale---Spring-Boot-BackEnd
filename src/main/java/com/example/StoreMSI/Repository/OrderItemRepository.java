package com.example.StoreMSI.Repository;

import com.example.StoreMSI.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>,
        JpaSpecificationExecutor<OrderItem> {
}