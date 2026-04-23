package com.restaurant.ex5.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "vendors")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;
    private String vendorName;
    private Double balance;

    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    List<Product> products;
    @OneToMany(mappedBy = "vendor", fetch = FetchType.LAZY)
    List<Order> orders;
}
