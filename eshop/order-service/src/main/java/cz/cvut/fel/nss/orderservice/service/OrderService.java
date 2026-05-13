package cz.cvut.fel.nss.orderservice.service;

import cz.cvut.fel.nss.orderservice.dao.OrderRepository;
import cz.cvut.fel.nss.orderservice.dto.CreateOrderItemRequest;
import cz.cvut.fel.nss.orderservice.dto.CreateOrderRequest;
import cz.cvut.fel.nss.orderservice.dto.ProductDto;
import cz.cvut.fel.nss.orderservice.event.OrderCreatedEvent;
import cz.cvut.fel.nss.orderservice.kafka.OrderEventProducer;
import cz.cvut.fel.nss.orderservice.model.Order;
import cz.cvut.fel.nss.orderservice.model.OrderItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository,
                        OrderEventProducer orderEventProducer) {
        this.orderRepository = orderRepository;
        this.orderEventProducer = orderEventProducer;
    }

    public Order createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setUserId(request.getUserId());

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

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    private ProductDto getMockProduct(Long productId) {
        return new ProductDto(
                productId,
                "Mock product",
                BigDecimal.valueOf(1000),
                10
        );
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
}