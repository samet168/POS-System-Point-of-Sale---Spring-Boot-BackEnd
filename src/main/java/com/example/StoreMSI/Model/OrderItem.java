package com.example.StoreMSI.Model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    // Relationship: Many order items belong to one order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Relationship: Many order items refer to one product
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}