package ca.ubc.cs304.delegates;

import ca.ubc.cs304.model.Customer;

public interface NewCustomerDelegate {
    void finishRegistration(Customer customer);
    void returnToCustomer();
}
