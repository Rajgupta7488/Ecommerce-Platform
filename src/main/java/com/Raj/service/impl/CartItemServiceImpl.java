package com.Raj.service.impl;


import com.Raj.model.CartItem;
import com.Raj.model.User;
import com.Raj.repository.CartItemRepository;
import com.Raj.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;



    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {
        CartItem item = findCartItemById(id) ;

        User cartItemsUsers = item.getCart().getUser();

        if (cartItemsUsers. getId() . equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
       throw new Exception("you can't update this cartItem");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {

        CartItem item = findCartItemById(cartItemId);

        User cartItemsUsers = item.getCart().getUser();

        if(cartItemsUsers.getId().equals(userId)){
        cartItemRepository.delete(item);}
else throw new Exception("you can't delete this item");

    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(()->
                new Exception("cart it not found "+id) ;
    }
}
