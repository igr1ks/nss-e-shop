package cz.cvut.fel.nss.order_service.model.state;

import cz.cvut.fel.nss.order_service.model.Order;

public class CompletedOrderState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Completed order cannot be paid again.");
    }

    @Override
    public void cancel(Order order) {
        throw new IllegalStateException("Completed order cannot be cancelled.");
    }

    @Override
    public void complete(Order order) {
        throw new IllegalStateException("Order is already completed.");
    }
}