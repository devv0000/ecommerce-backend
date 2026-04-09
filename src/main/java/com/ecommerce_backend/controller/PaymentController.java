package com.ecommerce_backend.controller;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import com.ecommerce_backend.entity.Payment;
import com.ecommerce_backend.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {

    private static final String KEY = "rzp_test_SaVW0dBOeGihJG";
    private static final String SECRET = "o5Y3c4pMN6uh3T8GeIROqUgH";

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // ✅ CREATE ORDER
    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestParam int amount) throws RazorpayException {

        RazorpayClient client = new RazorpayClient(KEY, SECRET);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100);
        options.put("currency", "INR");
        options.put("receipt", "order_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        return new JSONObject(order.toString()).toMap(); // ✅ CORRECT
    }

    // ✅ VERIFY PAYMENT
    @PostMapping("/verify")
    public boolean verifyPayment(@RequestBody Map<String, Object> data) {

        try {
            String orderId = data.get("razorpay_order_id").toString();
            String paymentId = data.get("razorpay_payment_id").toString();
            String signature = data.get("razorpay_signature").toString();
            int amount = Integer.parseInt(data.get("amount").toString());
            Long userId = Long.parseLong(data.get("userId").toString());

            String generatedSignature = hmacSHA256(orderId + "|" + paymentId, SECRET);

            boolean isValid = generatedSignature.equals(signature);

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setPaymentId(paymentId);
            payment.setSignature(signature);
            payment.setAmount(amount);
            payment.setUserId(userId);
            payment.setStatus(isValid ? "SUCCESS" : "FAILED");

            paymentService.savePayment(payment);

            return isValid;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA256"));

        byte[] rawHmac = mac.doFinal(data.getBytes());

        StringBuilder hex = new StringBuilder();
        for (byte b : rawHmac) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }
}