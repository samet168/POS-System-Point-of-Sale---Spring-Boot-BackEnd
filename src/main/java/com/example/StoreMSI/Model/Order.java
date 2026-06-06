package com.example.StoreMSI.Model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "received_amount", precision = 10, scale = 2)
    private BigDecimal receivedAmount;

    @Column(name = "change_amount", precision = 10, scale = 2)
    private BigDecimal changeAmount;

    // Relationship: Many orders can be created by one user (Cashier)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Relationship: One order has many order items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}