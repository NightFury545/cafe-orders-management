package org.nightfury.domain.entities.CustomerEntity.ValueObjects;

import org.nightfury.shared.Result;

public class FullName {
    private final String firstName;
    private final String lastName;

    private FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Result<FullName> create(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            return Result.failure("First name cannot be empty.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return Result.failure("Last name cannot be empty.");
        }

        if (firstName.length() < 2) {
            return Result.failure("First name must be at least 2 characters long.");
        }
        if (lastName.length() < 2) {
            return Result.failure("Last name must be at least 2 characters long.");
        }

        return Result.success(new FullName(firstName, lastName));
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FullName other) {
            return this.firstName.equals(other.firstName) && this.lastName.equals(other.lastName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() * 31 + lastName.hashCode();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}

