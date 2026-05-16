package cz.cvut.fel.nss.order_service.service;

import cz.cvut.fel.nss.order_service.dao.PaymentRepository;
import cz.cvut.fel.nss.order_service.model.Order;
import cz.cvut.fel.nss.order_service.model.Payment;
import cz.cvut.fel.nss.order_service.model.PaymentType;
import cz.cvut.fel.nss.order_service.service.strategy.PaymentStrategy;
import cz.cvut.fel.nss.order_service.service.strategy.PaymentStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final PaymentStrategyFactory paymentStrategyFactory;

    public Payment createPayment(Long orderId, PaymentType type) {
        Order order = orderService.getOrderById(orderId);

        PaymentStrategy strategy = paymentStrategyFactory.getStrategy(type);

        Payment payment = strategy.pay(order);

        orderService.payOrder(orderId);

        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }
}