package org.nightfury.domain.aggregates.OrderAggregate;

import org.nightfury.domain.aggregates.OrderAggregate.ValueObjects.TotalAmount;
import org.nightfury.domain.aggregates.OrderAggregate.enums.OrderStatus;
import org.nightfury.domain.entities.CustomerEntity.Customer;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.shared.Result;

public class Order {
    private final Id id;
    private final Customer customer;
    private final TotalAmount totalAmount;
    private OrderStatus status;

    private Order(Id id, Customer customer, OrderStatus status, TotalAmount totalAmount) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public static Result<Order> create(Customer customer, double amount) {
        Result<TotalAmount> totalAmountResult = TotalAmount.create(amount);
        if (!totalAmountResult.isSuccess()) {
            return Result.failure(totalAmountResult.getError());
        }

        Result<Id> idResult = Id.createNew();
        if (!idResult.isSuccess()) {
            return Result.failure(idResult.getError());
        }

        return Result.success(new Order(
            idResult.getValue(),
            customer,
            OrderStatus.PLACED,
            totalAmountResult.getValue()
        ));
    }

    public Result<Void> payOrder() {
        if (this.status != OrderStatus.PLACED) {
            return Result.failure("Замовлення можна оплатити тільки після розміщення.");
        }
        this.status = OrderStatus.PAID;
        return Result.success(null);
    }

    public Result<Void> cancelOrder() {
        if (this.status == OrderStatus.PAID) {
            return Result.failure("Оплачене замовлення не можна скасувати.");
        }
        this.status = OrderStatus.CANCELLED;
        return Result.success(null);
    }

    public Id getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TotalAmount getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Order other) {
            return this.id.equals(other.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Order { id=" + id + ", customerName=" + customer.getFullName() + ", status=" + status + ", totalAmount=" + totalAmount + " }";
    }
}
