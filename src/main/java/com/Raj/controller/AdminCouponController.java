package com.Raj.controller;

import com.Raj.model.Cart;
import com.Raj.model.Coupon;
import com.Raj.model.User;
import com.Raj.response.APIResponse;
import com.Raj.service.CartService;
import com.Raj.service.CouponService;
import com.Raj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")

public class AdminCouponController {

    private final CouponService couponService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon(
            @RequestParam String apply,
            @RequestParam String code,
            @RequestParam double orderValue,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart;

        if (apply.equals("true")) {
            cart = couponService.applyCoupon(code, orderValue, user);
        } else {
            cart = couponService.removeCoupon(code, user);
        }

        return ResponseEntity.ok(cart);
    }

    @RestController
    @RequestMapping("/admin")
    public class AdminCouponController {

        @PostMapping("/create")
        public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);
        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<APIResponse> deleteCoupon(@PathVariable Long id) throws Exception {
            couponService.deleteCoupon(id);
            APIResponse response = new APIResponse();
            response.setMessage("Coupon deleted successfully");
            response.setStatus(true);
            return ResponseEntity.ok(response);
        }

        @GetMapping("/all")
        public ResponseEntity<List<Coupon>> getAllCoupons() {
            List<Coupon> coupons = couponService.findAllCoupons();
            return ResponseEntity.ok(coupons);
        }
    }


}
