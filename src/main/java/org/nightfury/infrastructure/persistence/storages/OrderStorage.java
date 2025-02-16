package org.nightfury.infrastructure.persistence.storages;

import org.nightfury.domain.aggregates.OrderAggregate.Order;
import org.nightfury.domain.valueobjects.Id;

import java.util.HashMap;
import java.util.Map;

public class OrderStorage {

    private static final OrderStorage INSTANCE = new OrderStorage();
    private final Map<Id, Order> storage = new HashMap<>();

    private OrderStorage() {
    }

    public static OrderStorage getInstance() {
        return INSTANCE;
    }

    public Map<Id, Order> getStorage() {
        return storage;
    }
}

