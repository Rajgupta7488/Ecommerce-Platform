package com.Raj.controller;

import com.Raj.model.Product;
import com.Raj.model.User;
import com.Raj.model.Wishlist;
import com.Raj.service.ProductService;
import com.Raj.service.UserService;
import com.Raj.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/wishlist")

public class WishListController {

    private final WishListService wishlistService;
    private final UserService userService;
    private final ProductService productService;


    @GetMapping()
    public ResponseEntity<Wishlist> getWish1istByUserId(
            @RequestHeader("Authorization") String jwt) throws Exception {
    User user = userService.findUserByJwtToken(jwt);
    Wishlist wishlist = WishListService.getWishlistByUserId(user);
    return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}" )
    public ResponseEntity<Wishlist> addProductToWishList(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);

        Wishlist updatedWishlist = WishListService.addProductToWishlist(
                user,
                product
        );

        return ResponseEntity.ok(updatedWishlist);

    }
}


