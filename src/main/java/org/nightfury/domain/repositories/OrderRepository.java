package org.nightfury.domain.repositories;

import java.util.List;
import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.valueobjects.Id;

public interface OrderRepository extends BaseRepository<Order, Id> {

    List<Order> findByCustomerId(Id customerId);

    List<Order> findByStatus(String status);

    List<Order> findByTotalAmountRange(double minAmount, double maxAmount);
}
