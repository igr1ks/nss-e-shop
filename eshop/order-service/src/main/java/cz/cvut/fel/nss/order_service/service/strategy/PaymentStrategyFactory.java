package cz.cvut.fel.nss.order_service.service.strategy;

import cz.cvut.fel.nss.order_service.model.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyFactory {

    private final CardPaymentStrategy cardPaymentStrategy;
    private final CashPaymentStrategy cashPaymentStrategy;

    public PaymentStrategy getStrategy(PaymentType paymentType) {
        return switch (paymentType) {
            case CARD -> cardPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}