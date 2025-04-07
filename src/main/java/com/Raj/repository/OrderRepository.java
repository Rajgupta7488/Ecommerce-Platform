package com.Raj.repository;

import com.Raj.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,Long> {

    List<Orders> findByUserId(Long id);
    Orders findBySellerId(Long sellerId);

}
