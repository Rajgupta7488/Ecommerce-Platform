package com.Raj.service;

import com.Raj.domain.OrderStatus;
import com.Raj.model.*;


import java.util.List;
import java.util.Set;

public interface OrderService {


        Set<Orders> createOrder(User user, Address shippingAddress, Cart cart);

        Orders findOrderById(long id) throws Exception;

        List<Orders> usersOrderHistory(Long userId);

        List<Orders> sellersOrder(Long sellerId);

        Orders updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;

        Orders cancelOrder(Long orderId, User user) throws Exception;
        OrderItem getOrderItemById(Long id) throws Exception;
    }



