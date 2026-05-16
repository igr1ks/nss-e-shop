package cz.cvut.fel.nss.order_service.service;

import cz.cvut.fel.nss.order_service.dao.OrderRepository;
import cz.cvut.fel.nss.order_service.dto.CreateOrderItemRequest;
import cz.cvut.fel.nss.order_service.dto.CreateOrderRequest;
import cz.cvut.fel.nss.order_service.dto.ProductDto;
import cz.cvut.fel.nss.order_service.event.OrderCreatedEvent;
import cz.cvut.fel.nss.order_service.kafka.OrderEventProducer;
import cz.cvut.fel.nss.order_service.model.Order;
import cz.cvut.fel.nss.order_service.model.OrderItem;
import cz.cvut.fel.nss.order_service.model.state.OrderState;
import cz.cvut.fel.nss.order_service.model.state.OrderStateFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public Order createOrder(String email, CreateOrderRequest request) {
        Order order = new Order();

        Long userId = Math.abs((long) email.hashCode());
        order.setUserId(userId);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (CreateOrderItemRequest itemRequest : request.getItems()) {
            ProductDto product = getMockProduct(itemRequest.getProductId());

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new IllegalArgumentException("Not enough product stock");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(itemRequest.getQuantity());

            order.getItems().add(orderItem);

            totalPrice = totalPrice.add(
                    product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()))
            );
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        orderEventProducer.sendOrderCreatedEvent(
                new OrderCreatedEvent(
                        savedOrder.getId(),
                        savedOrder.getUserId(),
                        savedOrder.getTotalPrice()
                )
        );

        return savedOrder;
    }

    public Order payOrder(Long id) {
        Order order = findById(id);

        OrderState state = OrderStateFactory.getState(order.getStatus());
        state.pay(order);

        return orderRepository.save(order);
    }

    public Order cancelOrder(Long id) {
        Order order = findById(id);

        OrderState state = OrderStateFactory.getState(order.getStatus());
        state.cancel(order);

        return orderRepository.save(order);
    }

    public Order completeOrder(Long id) {
        Order order = findById(id);

        OrderState state = OrderStateFactory.getState(order.getStatus());
        state.complete(order);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    private ProductDto getMockProduct(Long productId) {
        return new ProductDto(
                productId,
                "Mock product",
                BigDecimal.valueOf(1000),
                10
        );
    }
}