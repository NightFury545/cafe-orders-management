package org.nightfury.domain.entities.CustomerEntity;

import org.nightfury.domain.entities.CustomerEntity.ValueObjects.Email;
import org.nightfury.domain.entities.CustomerEntity.ValueObjects.Address;
import org.nightfury.domain.entities.CustomerEntity.ValueObjects.Phone;
import org.nightfury.domain.entities.CustomerEntity.ValueObjects.FullName;
import org.nightfury.domain.valueobjects.Id;
import org.nightfury.shared.Result;

public class Customer {
    private final Id id;
    private final Email email;
    private final FullName fullName;
    private final Address address;
    private final Phone phone;

    private Customer(Id id, Email email, FullName fullName, Address address, Phone phone) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
    }

    public static Result<Customer> create(String email, String firstName, String lastName, String address, String phone) {
        Result<Id> idResult = Id.createNew();
        if (!idResult.isSuccess()) return Result.failure(idResult.getError());

        Result<Email> emailResult = Email.create(email);
        if (!emailResult.isSuccess()) return Result.failure(emailResult.getError());

        Result<FullName> fullNameResult = FullName.create(firstName, lastName);
        if (!fullNameResult.isSuccess()) return Result.failure(fullNameResult.getError());

        Result<Address> addressResult = Address.create(address);
        if (!addressResult.isSuccess()) return Result.failure(addressResult.getError());

        Result<Phone> phoneResult = Phone.create(phone);
        if (!phoneResult.isSuccess()) return Result.failure(phoneResult.getError());

        return Result.success(new Customer(
            idResult.getValue(),
            emailResult.getValue(),
            fullNameResult.getValue(),
            addressResult.getValue(),
            phoneResult.getValue()
        ));
    }

    public Id getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public FullName getFullName() {
        return fullName;
    }

    public Address getAddress() {
        return address;
    }

    public Phone getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Customer other) {
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
        return "Customer { id=" + id + ", email=" + email + ", fullName=" + fullName + ", address=" + address + ", phone=" + phone + " }";
    }
}

