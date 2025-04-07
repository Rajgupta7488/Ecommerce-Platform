package com.Raj.repository;


import com.Raj.model.Cart;
import com.Raj.model.CartItem;
import com.Raj.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);



}



