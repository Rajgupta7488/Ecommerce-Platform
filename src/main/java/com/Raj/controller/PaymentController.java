package com.Raj.controller;

import com.Raj.model.*;
import com.Raj.response.APIResponse;
import com.Raj.response.PaymentLinkResponse;
import com.Raj.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")

public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final SellerService sellerService;
    private final OrderService orderService;
    private final SellerReportService sellerReportService;
    private final TransactionService transactionService;



    @GetMapping("/{paymentId}")
    public ResponseEntity<APIResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        PaymentLinkResponse paymentResponse;

        PaymentOrder paymentOrder = paymentService
                .getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService.proceedPaymentOrder(
                paymentOrder,
                paymentId,
                paymentLinkId
        );

        if (paymentSuccess) {
            for (Orders order : paymentOrder.getOrders()) {
                transactionService.createTransaction(order);
                Seller seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);
                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarnings((long) (report.getTotalEarnings() + order.getTotalSellingPrice()));
                report.setTotalSales(report.getTotalSales() + order.getOrderItems().size());
                sellerReportService.updateSellerReport(report);

            }
        }
        APIResponse res = new APIResponse();
                res.setMessage("Payment successful");

                return new ResponseEntity<>(res, HttpStatus.CREATED);


            }
        }
