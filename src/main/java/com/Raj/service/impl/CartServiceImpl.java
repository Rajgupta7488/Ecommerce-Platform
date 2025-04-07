package com.Raj.service.impl;


import com.Raj.model.Cart;
import com.Raj.model.CartItem;
import com.Raj.model.Product;
import com.Raj.model.User;
import com.Raj.repository.CartItemRepository;
import com.Raj.repository.UserRepository;
import com.Raj.response.CartRepository;
import com.Raj.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) {

        Cart cart=findUserCart(user);

        CartItem isPresent=cartItemRepository . findByCartAndProductAndSize(cart, product, size) ;

        if(isPresent==null){
            CartItem cartItem=new CartItem();
            cartItem. setProduct (product) ;
            cartItem. setQuantity(quantity) ;
            cartItem.setSize(size);
            cartItem.setCart(cart);
           cartItem.setUserId(user.getId());

           int totalPrice=product.getMrpPrice()*quantity;
           cartItem.setSellingPrice(totalPrice);
           cartItem.setMrpPrice(quantity*product.getMrpPrice());

           cart.getCartItems().add(cartItem);
           cartItem.setCart(cart);

           return cartItemRepository.save(cartItem);
        }


        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {

        Cart cart=cartRepository.findByUserId(user.getId()) ;

        int totalPrice = 0;
        int totalDiscountPrice = 0;
        int totalItem = 0;

        for(CartItem cartItem:cart.getCartItems()){
            totalPrice+=cartItem.getMrpPrice();
            totalDiscountPrice+=cartItem.getSellingPrice();
            totalItem+=cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountPrice);
        cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountPrice));
        cart.setTotalItem(totalItem);
        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice){
        if (mrpPrice <= 0) {
            return 0;
        }

        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;

        return (int) discountPercentage;
    }
    }

