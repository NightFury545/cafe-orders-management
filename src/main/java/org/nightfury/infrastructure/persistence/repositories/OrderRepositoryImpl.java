package org.nightfury.infrastructure.persistence.repositories;

import java.math.BigDecimal;
import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.repositories.OrderRepository;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.infrastructure.persistence.storages.OrderStorage;
import org.nightfury.shared.Result;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private final OrderStorage storage = OrderStorage.getInstance();
    private final Map<Id, Order> orders = storage.getStorage();

    @Override
    public Result<Order> save(Order order) {
        if (order == null) {
            return Result.failure("Замовлення не може бути null.");
        }
        orders.put(order.getId(), order);
        return Result.success(order);
    }

    @Override
    public Optional<Order> findById(Id id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(orders.values());
    }

    @Override
    public Result<Void> deleteById(Id id) {
        if (!orders.containsKey(id)) {
            return Result.failure("Замовлення з ID " + id + " не знайдено.");
        }
        orders.remove(id);
        return Result.success(null);
    }

    @Override
    public List<Order> findByCustomerId(Id customerId) {
        return orders.values().stream()
            .filter(order -> order.getCustomer().getId().equals(customerId))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByStatus(String status) {
        return orders.values().stream()
            .filter(order -> order.getStatus().toString().equals(status))
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> findByTotalAmountRange(double minAmount, double maxAmount) {
        BigDecimal min = BigDecimal.valueOf(minAmount);
        BigDecimal max = BigDecimal.valueOf(maxAmount);

        return orders.values().stream()
            .filter(order -> order.getTotalAmount().getValue().compareTo(min) >= 0
                && order.getTotalAmount().getValue().compareTo(max) <= 0)
            .collect(Collectors.toList());
    }

}
