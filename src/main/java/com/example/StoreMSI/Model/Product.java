package com.example.StoreMSI.Model;


import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 50)
    private String barcode;

    @Column(name = "image_url", length = 255)
    private String imageUrl;

    @Column(name = "cost_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal costPrice;

    @Column(name = "selling_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal sellingPrice;

    @Column(nullable = false)
    private Integer quantity = 0;

    // Relationship: Many products belong to one category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Relationship: Many products belong to one brand
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}