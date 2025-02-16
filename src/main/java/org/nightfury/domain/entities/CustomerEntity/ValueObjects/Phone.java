package org.nightfury.domain.entities.CustomerEntity.ValueObjects;

import org.nightfury.shared.Result;
import java.util.regex.Pattern;

public class Phone {
    private final String value;

    private Phone(String value) {
        this.value = value;
    }

    public static Result<Phone> create(String phone) {
        if (phone == null || phone.isEmpty()) {
            return Result.failure("Phone number cannot be empty.");
        }

        String phoneRegex = "^\\+?[0-9]{10,15}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        if (!pattern.matcher(phone).matches()) {
            return Result.failure("Invalid phone number format.");
        }

        return Result.success(new Phone(phone));
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Phone other) {
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

