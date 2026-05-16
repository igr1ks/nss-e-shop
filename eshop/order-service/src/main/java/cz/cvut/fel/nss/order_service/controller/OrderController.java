package cz.cvut.fel.nss.order_service.controller;

import cz.cvut.fel.nss.order_service.dto.CreateOrderRequest;
import cz.cvut.fel.nss.order_service.facade.OrderFacade;
import cz.cvut.fel.nss.order_service.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderFacade.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderFacade.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateOrderRequest request
    ) {
        return orderFacade.createOrder(authHeader, request);
    }

    @PostMapping("/{id}/cancel")
    public Order cancelOrder(@PathVariable Long id) {
        return orderFacade.cancelOrder(id);
    }

    @PostMapping("/{id}/complete")
    public Order completeOrder(@PathVariable Long id) {
        return orderFacade.completeOrder(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderFacade.deleteOrder(id);
    }
}