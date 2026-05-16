package cz.cvut.fel.nss.order_service.model.state;

import cz.cvut.fel.nss.order_service.model.Order;
import cz.cvut.fel.nss.order_service.model.OrderStatus;

public class PaidOrderState implements OrderState {

    @Override
    public void pay(Order order) {
        throw new IllegalStateException("Order is already paid.");
    }

    @Override
    public void cancel(Order order) {
        order.setStatus(OrderStatus.CANCELLED);
    }

    @Override
    public void complete(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
    }
}