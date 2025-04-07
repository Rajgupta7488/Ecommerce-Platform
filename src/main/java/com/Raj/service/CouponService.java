package com.Raj.service;

import com.Raj.model.Cart;
import com.Raj.model.Coupon;
import com.Raj.model.User;

import java.util.List;
import com.Raj.model.Coupon;

public interface CouponService {

    Cart applyCoupon(String code, double orderValue, User user) throws Exception;

    Cart removeCoupon(String code, User user) throws Exception;

    Coupon findCouponById(Long id) throws Exception;

    Coupon createCoupon(Coupon coupon);

    List<Coupon> findAllCoupons();

    void deleteCoupon(Long id) throws Exception;

}
