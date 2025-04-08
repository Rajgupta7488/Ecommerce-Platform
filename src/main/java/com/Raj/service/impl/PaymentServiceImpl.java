package com.Raj.service.impl;

import com.Raj.domain.PaymentOrderStatus;
import com.Raj.domain.AccountStatus.PaymentStatus;
import com.Raj.model.Orders;
import com.Raj.model.PaymentOrder;
import com.Raj.model.User;
import com.Raj.repository.OrderRepository;
import com.Raj.repository.PaymentOrderRepository;
import com.Raj.service.PaymentService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final OrderRepository orderRepository;

    @Value("${razorpay.api.key}")
    private String APIKey;

    @Value("${razorpay.api.secret}")
    private String APISecret;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Override
    public PaymentOrder createOrder(User user, Set<Orders> orders) {
        // Fix the method reference issue by explicitly using a lambda expression
        Long amount = orders.stream()
                .mapToLong(order -> (long) order.getTotalSellingPrice())
                .sum();

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setOrders(orders);

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
        return paymentOrderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("Payment order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(orderId);
        if (paymentOrder == null) {
            throw new Exception("Payment order not found with provided payment link ID");
        }
        return paymentOrder;
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder,
                                       String paymentId,
                                       String paymentLinkId) throws RazorpayException {

        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)) {

            RazorpayClient razorpay = new RazorpayClient(APIKey, APISecret);
            Payment payment = razorpay.payments.fetch(paymentId);

            String status = payment.get("status");
            if (status.equals("captured")) {
                Set<Orders> orders = paymentOrder.getOrders();

                for (Orders order : orders) {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                    orderRepository.save(order);
                }

                paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);
                paymentOrder.setPaymentLinkId(paymentLinkId);
                paymentOrderRepository.save(paymentOrder);

                return true;
            }
            paymentOrder.setStatus(PaymentOrderStatus.FAILED);
            paymentOrderRepository.save(paymentOrder);
            return false;
        }

        return false;
    }

    // Extracted method for creating payment link request
    private JSONObject createPaymentLinkRequest(User user, Long amount) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());

        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);

        paymentLinkRequest.put("notify", notify);

        return paymentLinkRequest;
    }

    @Override
    public PaymentLink createRazorpayPaymentLink(User user, Long amount, Long orderId) throws RazorpayException {
        amount = amount * 100;
        try {
            RazorpayClient razorpay = new RazorpayClient(APIKey, APISecret);

            // Use the extracted method
            JSONObject paymentLinkRequest = createPaymentLinkRequest(user, amount);
            PaymentLink paymentLink = razorpay.paymentLink.create(paymentLinkRequest);

            // Get the payment order and update it with the payment link ID
            PaymentOrder paymentOrder = paymentOrderRepository.findById(orderId)
                    .orElseThrow(() -> new RazorpayException("Payment order not found"));

            String paymentLinkId = paymentLink.get("id");
            paymentOrder.setPaymentLinkId(paymentLinkId);
            paymentOrderRepository.save(paymentOrder);

            return paymentLink;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RazorpayException(e.getMessage());
        }
    }

    @Override
    public String createStripePaymentLink(User user, Long amount, Long orderId) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8081/payment-success/" + orderId)
                .setCancelUrl("http://localhost:8081/payment-cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(amount*100)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Order Payment")
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        try {
            Session session = Session.create(params);

            // Get the payment order and update it with session ID as payment link ID
            PaymentOrder paymentOrder = paymentOrderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Payment order not found"));
            paymentOrder.setPaymentLinkId(session.getId());
            paymentOrderRepository.save(paymentOrder);

            return session.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException("Failed to create Stripe payment session", e);
        }
    }
}