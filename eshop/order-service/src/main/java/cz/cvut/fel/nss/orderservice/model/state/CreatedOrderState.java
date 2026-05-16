package cz.cvut.fel.nss.orderservice.model.state;

import cz.cvut.fel.nss.orderservice.model.Order;
import cz.cvut.fel.nss.orderservice.model.OrderStatus;

public class CreatedOrderState implements OrderState {

    @Override
    public void pay(Order order) {
        order.setStatus(OrderStatus.PAID);
    }

    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
    }

    @Override
    public void complete(Order order) {
        throw new IllegalStateException("Order must be paid before completion.");
    }
}