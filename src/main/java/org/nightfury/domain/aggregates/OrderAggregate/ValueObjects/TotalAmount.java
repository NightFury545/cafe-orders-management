package org.nightfury.domain.aggregates.OrderAggregate.ValueObjects;

import org.nightfury.shared.Result;

import java.math.BigDecimal;

public class TotalAmount {
    private final BigDecimal value;

    private TotalAmount(BigDecimal value) {
        this.value = value;
    }

    public static Result<TotalAmount> create(double amount) {
        if (amount < 0) {
            return Result.failure("Сума замовлення не може бути від'ємною.");
        }
        return Result.success(new TotalAmount(BigDecimal.valueOf(amount)));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

