package com.Raj.model;

import com.Raj.domain.AccountStatus;

import lombok.Data;

@Data

public class PaymentDetails {

    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentIdZWSP;
    private AccountStatus.PaymentStatus status;



}
