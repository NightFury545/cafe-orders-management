package org.nightfury.domain.services;

import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.shared.Result;

public interface PaymentGateway {
    Result<Void> processPayment(Order order, double amount);
}
