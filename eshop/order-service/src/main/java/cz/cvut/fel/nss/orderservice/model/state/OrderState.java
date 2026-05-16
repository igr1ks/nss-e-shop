package cz.cvut.fel.nss.orderservice.model.state;

import cz.cvut.fel.nss.orderservice.model.Order;

public interface OrderState {
    void pay(Order order);
    void cancel(Order order);
    void complete(Order order);
}