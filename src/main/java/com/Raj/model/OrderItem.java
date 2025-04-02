package com.Raj.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_items") // Changed table name
@EqualsAndHashCode
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id") // Explicit join column name
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "product_id") // Explicit join column name
    private Product product;

    private String size;
    private int quantity;
    private Integer mrpPrice;
    private Integer sellingPrice;

    @Column(name = "customer_id") // Renamed column to avoid conflict
    private long userId;
}