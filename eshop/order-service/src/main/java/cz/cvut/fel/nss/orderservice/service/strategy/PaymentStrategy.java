package cz.cvut.fel.nss.orderservice.service.strategy;

import cz.cvut.fel.nss.orderservice.model.Order;
import cz.cvut.fel.nss.orderservice.model.Payment;

public interface PaymentStrategy {

    Payment pay(Order order);
}