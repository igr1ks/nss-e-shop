package cz.cvut.fel.nss.order_service.service.strategy;

import cz.cvut.fel.nss.order_service.model.Order;
import cz.cvut.fel.nss.order_service.model.Payment;
import cz.cvut.fel.nss.order_service.model.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public Payment pay(Order order) {
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setType(PaymentType.CARD);
        return payment;
    }
}