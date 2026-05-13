package cz.cvut.fel.nss.orderservice.service;

import cz.cvut.fel.nss.orderservice.dao.PaymentRepository;
import cz.cvut.fel.nss.orderservice.model.Payment;
import cz.cvut.fel.nss.orderservice.model.PaymentType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    public PaymentService(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

    public Payment createPayment(Long orderId, PaymentType type) {
        orderService.getOrderById(orderId);

        Payment payment = new Payment(orderId, type);

        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }
}