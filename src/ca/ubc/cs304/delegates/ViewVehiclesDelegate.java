package ca.ubc.cs304.delegates;

public interface ViewVehiclesDelegate {
    void submit(String carType, String location, String city);
    void returnToCustomer();
}
