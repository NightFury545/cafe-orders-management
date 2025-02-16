package org.nightfury.domain.services;

import java.math.BigDecimal;
import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.repositories.OrderRepository;
import org.nightfury.shared.Result;

public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    public PaymentService(OrderRepository orderRepository, PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public Result<Void> processPayment(Order order, double amount) {
        if (order.getTotalAmount().getValue().compareTo(BigDecimal.valueOf(amount)) != 0) {
            return Result.failure("Невірна сума платежу.");
        }

        Result<Void> paymentResult = paymentGateway.processPayment(order, amount);
        if (!paymentResult.isSuccess()) {
            return Result.failure("Помилка при обробці платежу.");
        }

        order.payOrder();
        orderRepository.save(order);

        return Result.success(null);
    }
}

