package cz.cvut.fel.nss.order_service.service.strategy;

import cz.cvut.fel.nss.order_service.model.Order;
import cz.cvut.fel.nss.order_service.model.Payment;

public interface PaymentStrategy {

    Payment pay(Order order);
}