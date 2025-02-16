package org.nightfury.infrastructure.services;

import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.services.PaymentGateway;
import org.nightfury.shared.Result;

public class PayPalPaymentGateway implements PaymentGateway {

    @Override
    public Result<Void> processPayment(Order order, double amount) {
        System.out.println("Обробка платежу через PayPal для замовлення " + order.getId());
        return Result.success(null);
    }
}
