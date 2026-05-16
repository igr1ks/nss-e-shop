package cz.cvut.fel.nss.orderservice.facade;

import cz.cvut.fel.nss.orderservice.dto.CreateOrderRequest;
import cz.cvut.fel.nss.orderservice.model.Order;
import cz.cvut.fel.nss.orderservice.model.Payment;
import cz.cvut.fel.nss.orderservice.model.PaymentType;
import cz.cvut.fel.nss.orderservice.service.OrderService;
import cz.cvut.fel.nss.orderservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public Order createOrder(CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    public Payment payOrder(Long orderId, PaymentType paymentType) {
        return paymentService.createPayment(orderId, paymentType);
    }

    public Order cancelOrder(Long orderId) {
        return orderService.cancelOrder(orderId);
    }

    public Order completeOrder(Long orderId) {
        return orderService.completeOrder(orderId);
    }

    public Order getOrderById(Long orderId) {
        return orderService.getOrderById(orderId);
    }

    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderService.getOrdersByUserId(userId);
    }

    public void deleteOrder(Long orderId) {
        orderService.delete(orderId);
    }
}