package com.microtech.smartshop.model;

import com.microtech.smartshop.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private CustomerTier tier = CustomerTier.BASIC;

    private double totalSpent = 0.0;
    private int totalOrders = 0;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}