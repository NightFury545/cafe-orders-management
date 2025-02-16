package org.nightfury.domain.entities.CustomerEntity.ValueObjects;

import org.nightfury.shared.Result;

public class Address {
    private final String value;

    private Address(String value) {
        this.value = value;
    }

    public static Result<Address> create(String address) {
        if (address == null || address.trim().isEmpty()) {
            return Result.failure("Address cannot be empty.");
        }

        if (address.length() < 5) {
            return Result.failure("Address must be at least 5 characters long.");
        }

        return Result.success(new Address(address));
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Address other) {
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

