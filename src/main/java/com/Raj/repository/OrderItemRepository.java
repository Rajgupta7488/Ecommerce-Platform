package com.Raj.repository;

import com.Raj.model.OrderItem;
import com.Raj.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
