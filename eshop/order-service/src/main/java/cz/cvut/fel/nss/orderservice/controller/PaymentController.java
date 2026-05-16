package cz.cvut.fel.nss.orderservice.controller;

import cz.cvut.fel.nss.orderservice.model.Payment;
import cz.cvut.fel.nss.orderservice.model.PaymentType;
import cz.cvut.fel.nss.orderservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.findAll();
    }

    @PostMapping("/orders/{orderId}")
    public Payment createPayment(
            @PathVariable Long orderId,
            @RequestParam PaymentType type
    ) {
        return paymentService.createPayment(orderId, type);
    }
}