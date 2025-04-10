package com.Raj.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Set;


import java.util.HashSet;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;

    private int totalItem;

    private int totalMrpPrice;

    private int Discount;

    private String couponCode;


}
