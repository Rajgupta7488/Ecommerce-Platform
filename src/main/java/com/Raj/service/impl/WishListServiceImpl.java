package com.Raj.service.impl;

import com.Raj.model.Product;
import com.Raj.model.User;
import com.Raj.model.Wishlist;
import com.Raj.repository.WishListRepository;
import com.Raj.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishlistRepository;
    @Override
    public Wishlist createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);

    }

    @Override
    public Wishlist getWishlistByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());
        if(wishlist==null) {
            wishlist = createWishlist(user);
        }
        return  wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = getWishlistByUserId(user);

        if (!wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
        }
        else wishlist.getProducts().add(product);


        return wishlistRepository.save(wishlist);
    }
}
