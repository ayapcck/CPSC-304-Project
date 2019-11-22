package ca.ubc.cs304.delegates;

public interface NewCustomerDelegate {
    void finishRegistration(String name, String phone, String license, String address);
    void returnToCustomer();
}
