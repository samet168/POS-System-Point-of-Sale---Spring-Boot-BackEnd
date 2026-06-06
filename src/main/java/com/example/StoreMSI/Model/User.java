package com.example.StoreMSI.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= BASIC INFO =================
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    // ================= ROLE =================
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.cashier;

    public enum Role {
        admin,
        cashier
    }

    // ================= STATUS =================
    @Column(nullable = false)
    private Boolean status = true; // true = active, false = inactive

    // ================= AUDIT =================
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ================= LIFECYCLE =================
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = true;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}