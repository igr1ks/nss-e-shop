package cz.cvut.fel.nss.order_service.model.state;

import cz.cvut.fel.nss.order_service.model.Order;

public class CancelledOrderState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Cancelled order cannot be paid.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Order is already cancelled.");
    }

    @Override
    public void complete(Order order) {
        throw new IllegalStateException("Cancelled order cannot be completed.");
    }
}