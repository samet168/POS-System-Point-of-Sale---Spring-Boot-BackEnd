package com.example.StoreMSI.Model;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // Relationship: One brand can belong to many products
    @OneToMany(mappedBy = "brand", cascade = CascadeType.PERSIST)
    private List<Product> products;
}