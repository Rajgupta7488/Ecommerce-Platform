package com.Raj.service;

import com.Raj.model.Cart;
import com.Raj.model.CartItem;
import com.Raj.model.Product;
import com.Raj.model.User;

public interface CartService {

    public CartItem addCartItem(
            User user,
            Product product,
            String size,
            int quantity
    );
    public Cart findUserCart(User user);
}
