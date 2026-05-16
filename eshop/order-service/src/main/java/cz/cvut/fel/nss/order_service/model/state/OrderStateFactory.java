package cz.cvut.fel.nss.order_service.model.state;

import cz.cvut.fel.nss.order_service.model.OrderStatus;

public class OrderStateFactory {

    public static OrderState getState(OrderStatus status) {
        return switch (status) {
            case CREATED -> new CreatedOrderState();
            case PAID -> new PaidOrderState();
            case CANCELLED -> new CancelledOrderState();
            case COMPLETED -> new CompletedOrderState();
        };
    }
}