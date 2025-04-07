package com.Raj.repository;

import com.Raj.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository  extends JpaRepository<Wishlist,Long> {
    Wishlist findByUserId(Long id);
}
