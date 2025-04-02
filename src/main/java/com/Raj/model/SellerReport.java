package com.Raj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode



public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Seller seller;
    private Long totalEarnings = 0L;
    private Long totalSales =  0L;
    private Long totalRefunds= 0L;
    private Long totalTax = 0L;
    private Long netEarnings = 0L;
    private Integer totalOrders = 0;
    private Integer cancelOrders = 0;
    private Integer totalTransaction = 0;


}
