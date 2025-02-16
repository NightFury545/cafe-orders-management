package org.nightfury.domain.valueobjects;

import java.util.UUID;
import org.nightfury.shared.Result;

public class Id {
    private final String value;

    private Id(String value) {
        this.value = value;
    }

    public static Result<Id> create(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Result.failure("Id cannot be null or empty.");
        }

        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            return Result.failure("Id must be a valid UUID.");
        }

        return Result.success(new Id(value));
    }

    public static Result<Id> createNew() {
        return Result.success(new Id(UUID.randomUUID().toString()));
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Id other) {
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
