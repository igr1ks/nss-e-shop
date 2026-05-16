package cz.cvut.fel.nss.orderservice.service.strategy;
import cz.cvut.fel.nss.orderservice.model.Order;
import cz.cvut.fel.nss.orderservice.model.Payment;
import cz.cvut.fel.nss.orderservice.model.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public Payment pay(Order order) {
        Payment payment = new Payment();
        payment.setOrderId(order.getId());
        payment.setType(PaymentType.CASH);
        return payment;
    }
}