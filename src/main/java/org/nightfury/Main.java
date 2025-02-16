package org.nightfury;

import java.util.List;
import org.nightfury.application.services.OrderService;
import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.entities.CustomerEntity.Customer;
import org.nightfury.domain.services.PaymentService;
import org.nightfury.infrastructure.persistence.repositories.OrderRepositoryImpl;
import org.nightfury.infrastructure.services.PayPalPaymentGateway;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.injectors.ConstructorInjection;

public class Main {

    public static void main(String[] args) {
        MutablePicoContainer picoContainer = new DefaultPicoContainer(new ConstructorInjection());
        picoContainer.addComponent(OrderRepositoryImpl.class);
        picoContainer.addComponent(PaymentService.class);
        picoContainer.addComponent(PayPalPaymentGateway.class);
        picoContainer.addComponent(OrderService.class);

        OrderService orderService = picoContainer.getComponent(OrderService.class);
        Customer customer = Customer.create("nightfury123@gmail.com", "Ruslan", "NightFury",
            "Uzhhorod", "38026712412").getValue();

        Order order1 = orderService.createOrder(customer, 50).getValue();
        Order order2 = orderService.createOrder(customer, 75).getValue();

        orderService.payOrder(order1.getId(), 50);
        orderService.cancelOrder(order2.getId());

        List<Order> ordersHistory = orderService.getOrderHistory(customer.getId()).getValue();
        ordersHistory.forEach(System.out::println);
    }
}