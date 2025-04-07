package com.Raj.service;

import com.Raj.model.Product;
import com.Raj.model.User;
import com.Raj.model.Wishlist;

public interface WishListService {

    Wishlist createWishlist(User user);

    static Wishlist getWishlistByUserId(User user);

    static Wishlist addProductToWishlist(User user, Product product);


}
