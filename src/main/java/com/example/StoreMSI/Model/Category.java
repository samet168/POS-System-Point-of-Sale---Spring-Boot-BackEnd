package com.example.StoreMSI.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Getter
@Setter // Lombok នឹងបង្កើត setImageUrl() ឱ្យដោយស្វ័យប្រវត្តិ
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // បន្ថែម Field មួយនេះចូល
    @Column(name = "image_url")
    private String imageUrl;

    // បើសិនជាមាន Relationship ផ្សេងទៀត សូមទុកវានៅដដែល
}