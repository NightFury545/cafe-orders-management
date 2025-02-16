package org.nightfury.application.services;

import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.entities.CustomerEntity.Customer;
import org.nightfury.domain.repositories.OrderRepository;
import org.nightfury.domain.services.PaymentService;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.shared.Result;

import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public OrderService(OrderRepository orderRepository, PaymentService paymentService) {
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }

    public Result<Order> createOrder(Customer customer, double amount) {
        Result<Order> orderResult = Order.create(customer, amount);
        if (!orderResult.isSuccess()) {
            return Result.failure(orderResult.getError());
        }

        Order order = orderResult.getValue();
        return orderRepository.save(order);
    }

    public Result<Void> payOrder(Id orderId, double amount) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return Result.failure("Замовлення не знайдено.");
        }

        Order order = orderOpt.get();
        return paymentService.processPayment(order, amount);
    }

    public Result<Void> cancelOrder(Id orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return Result.failure("Замовлення не знайдено.");
        }

        Order order = orderOpt.get();
        return order.cancelOrder();
    }

    public Result<List<Order>> getOrderHistory(Id customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        if (orders.isEmpty()) {
            return Result.failure("Замовлень для користувача не знайдено.");
        }

        return Result.success(orders);
    }
}
