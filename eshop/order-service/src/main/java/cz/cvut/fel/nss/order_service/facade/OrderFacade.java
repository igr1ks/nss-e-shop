package cz.cvut.fel.nss.order_service.facade;

import cz.cvut.fel.nss.order_service.dto.CreateOrderRequest;
import cz.cvut.fel.nss.order_service.model.Order;
import cz.cvut.fel.nss.order_service.model.Payment;
import cz.cvut.fel.nss.order_service.model.PaymentType;
import cz.cvut.fel.nss.order_service.service.JwtService;
import cz.cvut.fel.nss.order_service.service.OrderService;
import cz.cvut.fel.nss.order_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final JwtService jwtService;
    public Order createOrder(String authHeader, CreateOrderRequest request) {

        String token = authHeader.substring(7);

        String email = jwtService.extractEmail(token);

        return orderService.createOrder(email, request);
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