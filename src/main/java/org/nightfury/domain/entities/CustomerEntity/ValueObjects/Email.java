package org.nightfury.domain.entities.CustomerEntity.ValueObjects;

import org.nightfury.shared.Result;
import java.util.regex.Pattern;

public class Email {
    private final String value;

    private Email(String value) {
        this.value = value;
    }

    public static Result<Email> create(String email) {
        if (email == null || email.isEmpty()) {
            return Result.failure("Email cannot be empty.");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            return Result.failure("Invalid email format.");
        }

        return Result.success(new Email(email));
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Email other) {
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

